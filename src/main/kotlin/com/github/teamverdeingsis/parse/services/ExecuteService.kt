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

    fun hola(): String {
        return "Hola"
    }

    fun executeSnippet(code: String, version: String): String {
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
        val printEmitter = PrintEmitter()
        val interpreter = Interpreter(parser, ConsoleInputProvider(), printEmitter, ErrorCollector())

        val result = interpreter.interpret()
        return result.toString()
    }

        fun test(version: String, snippetId: String, inputs: List<String>, outputs: List<String>): List<String> {
        val codeToInputStream: InputStream = snippetId.byteInputStream()
        val reader = Reader(codeToInputStream)
        val lexer = when (version) {
            "1.1" -> LexerFactory().createLexer1_1(reader)
            else -> LexerFactory().createLexer1_0(reader)
        }
        val parser = when (version) {
            "1.1" -> ParserFactory().createParser1_1(lexer)
            else -> ParserFactory().createParser1_0(lexer)
        }
        val printEmitter = PrintEmitter()
        val interpreter = Interpreter(parser, ConsoleInputProvider(), printEmitter, ErrorCollector())

        val result = interpreter.interpret()
        return compareResults(listOf(result.toString()), outputs)
    }

    fun compareResults(results: List<String>, outputs: List<String>): List<String> {
        val finalResults = mutableListOf<String>()

        outputs.zip(results) { expected, actual ->
            if (expected != actual) {
                finalResults.add("Expected '$expected' but got '$actual'")
            }
        }

        if (outputs.size < results.size) {
            for (i in outputs.size until results.size) {
                finalResults.add("Unexpected extra output: ${results[i]}")
            }
        }

        else if (results.size < outputs.size) {
            for (i in results.size until outputs.size) {
                finalResults.add("Missing expected output: ${outputs[i]}")
            }
        }
        return finalResults
    }
}