package com.github.teamverdeingsis.parse.services

import factory.LexerFactory
import factory.ParserFactory
import org.springframework.stereotype.Service
import reader.Reader
import java.io.InputStream

@Service
class ValidateService {
    fun validateSnippet(code: String, version: String): List<String> {
        val codeToInputStream: InputStream = code.byteInputStream()
        val reader = Reader(codeToInputStream)
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }
        val parser = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }
        val results = mutableListOf<String>()
        while (parser.hasNextAST()) {
            try {
                parser.nextStatement()
            } catch (e: Exception) {
                results.add(e.message ?: "Unknown error")
            }
        }
        return results
    }
}