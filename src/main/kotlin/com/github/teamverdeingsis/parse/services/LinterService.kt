package com.github.teamverdeingsis.parse.services

import factory.LexerFactory
import factory.LinterFactory
import factory.ParserFactory
import linter.LinterError
import org.springframework.stereotype.Service
import reader.Reader
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

@Service
class LinterService {
    fun lintSnippet(code: InputStream, version: String): List<LinterError> {
         val reader = Reader(code)
        val configFilePath =
            "C:\\Users\\vranc\\Projects\\Ingsis\\PrintScript2\\cli\\src\\main\\resources\\linter-config.json"
        val path = Paths.get(configFilePath)
        val rules: InputStream = Files.newInputStream(path)
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }

        val parserDirector = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }

        val linter = when (version) {
            "1.1" -> LinterFactory().createLinter1_1(parserDirector, rules)
            else -> LinterFactory().createLinter1_0(parserDirector, rules)
        }
        return linter.lint().toList()
    }
}