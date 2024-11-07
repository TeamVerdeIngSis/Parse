package com.github.teamverdeingsis.parse.services

import emitter.PrintEmitter
import errorCollector.ErrorCollector
import factory.LexerFactory
import factory.ParserFactory
import interpreter.Interpreter
import org.springframework.stereotype.Service
import provider.ConsoleInputProvider
import reader.Reader
import java.io.InputStream


@Service
class ExecuteService {
    fun executeSnippet(code: InputStream, version: String): String {
        val reader = Reader(code)
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }
        val parser = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }
        val interpreter = Interpreter(parser, ConsoleInputProvider(), PrintEmitter(), ErrorCollector())

        return interpreter.interpret().toString()
    }
}