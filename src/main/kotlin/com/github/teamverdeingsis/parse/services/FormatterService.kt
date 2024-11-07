package com.github.teamverdeingsis.parse.services

import factory.LexerFactory
import factory.ParserFactory
import formatter.Formatter
import formatter.FormatterConfigLoader
import org.springframework.stereotype.Service
import reader.Reader
import rules.*
import java.io.InputStream

@Service
class FormatterService {
    fun formatSnippet(code: InputStream, version: String, rules: String): String {
        val reader = Reader(code)
        val configFilePath =
            "C:\\Users\\vranc\\Projects\\Ingsis\\PrintScript2\\cli\\src\\main\\resources\\formatter-config.json"
        val config = FormatterConfigLoader.loadConfig(configFilePath)
        val rules = listOf(
            Indentation(config.indentation),
            NoSpaceAroundEqualsRule(config.spaceAroundEquals.enabled),
            SpaceBeforeColonRule(config.spaceBeforeColon.enabled),
            SpaceAfterColonRule(config.spaceAfterColon.enabled),
            // Add other rules based on the provided config
            NewlineBeforePrintlnRule(config.newlineBeforePrintln),
        )
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }
        val parser = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }
        val formatter = Formatter(rules, parser)
        return formatter.format().joinToString("\n")
    }
}