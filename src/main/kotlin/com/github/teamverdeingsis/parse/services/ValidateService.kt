package com.github.teamverdeingsis.parse.services

import factory.LexerFactory
import factory.ParserFactory
import org.springframework.stereotype.Service
import reader.Reader
import java.io.InputStream

@Service
class ValidateService {
    fun validateSnippet(code: InputStream, version: String): String {

        val reader = Reader(code)
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }
        val parser = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }
        while (parser.hasNextAST()) {
            val statement = parser.nextStatement()
            println(statement)
        }
        return "file validated"
    }
}