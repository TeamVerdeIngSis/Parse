package com.github.teamverdeingsis.parse.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.teamverdeingsis.parse.entity.FormattingConfig
import com.github.teamverdeingsis.parse.entity.Rule
import com.github.teamverdeingsis.parse.factory.RulesFactory
import factory.FormatterFactory
import factory.LexerFactory
import factory.ParserFactory
import org.springframework.stereotype.Service
import reader.Reader
import java.io.File
import java.io.InputStream
import java.util.stream.Collectors
import kotlin.streams.asStream

@Service
class FormatterService(private val assetService: AssetService) {

    fun formatSnippet(snippetId: String, userId: String): String {
        println("Formateando snippet $snippetId con las reglas del usuario $userId")
        val code = assetService.getAsset("snippets", snippetId) ?: ""
        println("codee $code")
        println("voy a buscar las reglas del usuario $userId")
        val rulesInString: String = if (assetService.assetExists("format", userId)) {
            println("che el asset existe")
            assetService.getAsset("format", userId) ?: ""
        } else {
            println("che el asset no existe")
            RulesFactory.defaultFormattingRules().toString()
        }

        println("rulesInString $rulesInString")
        val mapper = jacksonObjectMapper()
        val rules: List<Rule> = try {
            mapper.readValue(rulesInString)
        } catch (e: Exception) {
            println("Error deserializando reglas: ${e.message}")
            emptyList() // Usa una lista vacía si falla la deserialización
        }
        println("lista de rules $rules")
        // Convertir la lista de reglas en un objeto FormattingConfig
        val config = convertRulesToConfig(rules.filter { it.isActive })
        println("Configuración de formateo obtenida: $config")

        // Serializar la configuración a JSON y convertirla a InputStream
        val configJsonString = mapper.writeValueAsString(config)

        // Procesar el snippet usando la configuración actual
        val formattedCode = applyFormatting(code, configJsonString)

        try {
            assetService.updateAsset(snippetId, "snippets", formattedCode)
        } catch (e: Exception) {
            println("Error al actualizar el asset: ${e.message}")
        }

        return formattedCode
    }

    private fun applyFormatting(code: String, configJsonString: String): String {
        val tempFile = File.createTempFile("formatter-config", ".json")
        tempFile.writeText(configJsonString)
        println("here are the rules going to be used $configJsonString")

        val codeToInputStream: InputStream = code.byteInputStream()

        val reader = Reader(codeToInputStream)
        val lexer = LexerFactory().createLexer1_1(reader)
        val parserDirector = ParserFactory().createParser1_1(lexer)
        val formatter = FormatterFactory().createFormatter1_1(parserDirector, tempFile.inputStream())

        // Trabajar directamente con la colección devuelta
        val formattedLines = formatter.format() // Asumimos que devuelve una lista o iterable
        val formattedCode = formattedLines.joinToString(separator = "\n") // Unir líneas con saltos de línea

        return formattedCode
    }



    private fun convertRulesToConfig(rules: List<Rule>): FormattingConfig {
        val config = FormattingConfig()

        rules.forEach { rule ->
            when (rule.name) {
                "space-before-colon" -> config.spaceBeforeColon = rule.isActive
                "space-after-colon" -> config.spaceAfterColon = rule.isActive
                "space-around-equals" -> config.spaceAroundEquals = rule.isActive
                "newline-before-println" -> config.newlineBeforePrintln = rule.value?.toInt() ?: 0
                "indentation" -> config.indentation = rule.value?.toInt() ?: 4
                else -> println("Regla desconocida: ${rule.name}")
            }
        }

        return config
    }
}

