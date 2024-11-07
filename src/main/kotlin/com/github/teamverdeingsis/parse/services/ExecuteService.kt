package com.github.teamverdeingsis.parse.services

import emitter.PrintEmitter
import errorCollector.ErrorCollector
import factory.LexerFactory
import factory.ParserFactory
import interpreter.Interpreter
import org.springframework.stereotype.Service
import provider.ConsoleInputProvider
import reader.Reader
import java.util.Base64
import java.io.ByteArrayInputStream
import java.io.InputStream

import kotlin.io.encoding.ExperimentalEncodingApi


@Service
class ExecuteService {
    @OptIn(ExperimentalEncodingApi::class)
    fun executeSnippet(codeBase64: String, version: String): String {

        val decodedBytes = Base64.getDecoder().decode(codeBase64)
        val codeInputStream = ByteArrayInputStream(decodedBytes)
        val reader = Reader(codeInputStream)
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

    fun executeSnippet2(codeInputStream: InputStream, version: String): String {
        val reader = Reader(codeInputStream)
        println(version)

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