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
import com.github.teamverdeingsis.parse.entity.LintingConfig
import com.github.teamverdeingsis.parse.entity.Rule
import java.io.File

@Service
class LinterService(private val restTemplate: RestTemplate) {
    fun lintSnippet(snippetId: String, userId: String): List<LinterError> {


        // Obtén el código del snippet
        val snippetURL = "http://localhost:8080/v1/asset/snippets/$snippetId"
        val code = restTemplate.getForObject(snippetURL, String::class.java) ?: return emptyList()
        println("agarre el contenido $code")
        // Obtén las reglas de linting
        val lintingRulesURL = "http://localhost:8080/v1/asset/linting/$userId"
        val rulesJson = restTemplate.getForObject(lintingRulesURL, String::class.java) ?: return emptyList()
        println("agarre las reglas $rulesJson")

        // Deserializar las reglas
        val mapper = jacksonObjectMapper()
        val rules: List<Rule> = mapper.readValue(rulesJson)

        // Procesa cada regla activa
        val linterResults = mutableListOf<LinterError>()
        val filteredRules = rules.filter { it.isActive }
        println(filteredRules)
        val config = makeLintingConfig(filteredRules)

        // Serializar la configuración a JSON
        val configJsonString = mapper.writeValueAsString(config)

        // Escribe la configuración en un archivo temporal
        val tempFile = File.createTempFile("linter-config", ".json")
        tempFile.writeText(configJsonString)
        println("here are the rules going to be used $configJsonString")

        // Procesa el snippet con la configuración actual
        val codeToInputStream: InputStream = code.byteInputStream()
        val reader = Reader(codeToInputStream)

        val lexer = LexerFactory().createLexer1_1(reader)
        val parserDirector = ParserFactory().createParser1_1(lexer)
        val linter = LinterFactory().createLinter1_1(parserDirector, tempFile.inputStream())

        linterResults.addAll(linter.lint().toList())
        println("resultado del linteo $linterResults")
        return linterResults
    }


    private fun makeLintingConfig(rules: List<Rule>): LintingConfig {
        val config = LintingConfig()
        rules.forEach { rule ->
            when (rule.name) {
                "snake-case-variables" -> config.identifierFormat = "snake case"
                "camel-case-variables" -> config.identifierFormat = "camel case"
                "mandatory-variable-or-literal-in-println" -> config.mandatoryVariableOrLiteral = "true"
                "read-input-with-simple-argument" -> config.readInputWithSimpleArgument = "true"
                else -> println("Regla no soportada: ${rule.name}")
            }
        }
        return config
    }
}