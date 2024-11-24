package com.github.teamverdeingsis.parse.services

import factory.LexerFactory
import factory.LinterFactory
import factory.ParserFactory
import linter.LinterError
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import reader.Reader
import java.io.InputStream
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.teamverdeingsis.parse.entity.Rule
import java.io.File

@Service
class LinterService(private val restTemplate: RestTemplate) {
    fun lintSnippet(snippetId: String, userId: String): List<LinterError> {
        println("Llegué al linterService con estos valores $snippetId y $userId")

        // Obtén el código del snippet
        val snippetURL = "http://localhost:8083/v1/asset/snippets/$snippetId"
        val code = restTemplate.getForObject(snippetURL, String::class.java) ?: return emptyList()

        // Obtén las reglas de linting
        val lintingRulesURL = "http://localhost:8083/v1/asset/linting/$userId"
        val rulesJson = restTemplate.getForObject(lintingRulesURL, String::class.java) ?: return emptyList()

        println("NOSENOSENOSE")
        println(rulesJson)

        // Deserializar las reglas
        val mapper = jacksonObjectMapper()
        val rules: List<Rule> = mapper.readValue(rulesJson)

        // Procesa cada regla activa
        val linterResults = mutableListOf<LinterError>()
        rules.filter { it.isActive }.forEach { rule ->
            val config = when (rule.name) {
                "snake-case-variables" -> mapOf("identifier_format" to "snake case")
                "camel-case-variables" -> mapOf("identifier_format" to "camel case")
                "mandatory-variable-or-literal-in-println" -> mapOf("mandatory-variable-or-literal-in-println" to true)
                "read-input-with-simple-argument" -> mapOf("read-input-with-simple-argument" to true)
                else -> null
            }

            if (config != null) {
                // Serializar la configuración a JSON
                val configJsonString = mapper.writeValueAsString(config)

                // Escribe la configuración en un archivo temporal
                val tempFile = File.createTempFile("linter-config", ".json")
                tempFile.writeText(configJsonString)
                println("Archivo temporal creado para ${rule.name}: ${tempFile.absolutePath}")

                // Procesa el snippet con la configuración actual
                val codeToInputStream: InputStream = code.byteInputStream()
                val reader = Reader(codeToInputStream)

                val lexer = LexerFactory().createLexer1_1(reader)
                val parserDirector = ParserFactory().createParser1_1(lexer)
                val linter = LinterFactory().createLinter1_1(parserDirector, tempFile.inputStream())

                linterResults.addAll(linter.lint().toList())
            }
        }

        return linterResults
    }


}