package com.github.teamverdeingsis.parse.services

import emitter.PrintEmitter
import errorCollector.ErrorCollector
import factory.LexerFactory
import factory.ParserFactory
import interpreter.Interpreter
import org.springframework.stereotype.Service
import provider.ConsoleInputProvider
import provider.InputProvider
import reader.Reader
import java.io.InputStream

@Service
class ExecuteService(private val assetService: AssetService) {

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
        val code = assetService.getAsset("snippets", snippetId)
        println("Code: $code")
        val codeToInputStream: InputStream = code?.byteInputStream() ?: return listOf("Snippet not found")
        println("Code to input stream: $codeToInputStream")
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

        val inputAdapter = object : InputProvider {
            private val inputQueue = inputs.toMutableList()

            override fun readInput(prompt: String): Any {
                return if (inputQueue.isNotEmpty()) {
                    inputQueue.removeAt(0)
                } else {
                    "No input available"
                }
            }
        }

        val interpreter = Interpreter(parser, inputAdapter, printEmitter, ErrorCollector())

        val results = interpreter.interpret()
        val normalizedResults = normalizeResults(results)

        println("Interpreter results: $results")
        println("Normalized results: $normalizedResults")
        println("Original outputs: $outputs")

        val normalizedOutputs = normalizeResults(outputs)
        println("Normalized outputs: $normalizedOutputs")

        // Comparar resultados con los outputs esperados
        val finalResults = compareResults(normalizedResults, normalizedOutputs)
        println("Final results: $finalResults")
        return finalResults
    }

    fun normalizeResults(results: List<Any?>): List<String> {
        return results.map { normalizeValue(it) } // Reutilizar normalizeValue para consistencia
    }

    fun normalizeValue(value: Any?): String {
        return when (value) {
            is Number -> value.toString().removeSuffix(".0") // Eliminar el .0 si está presente
            is String -> value.trim() // Recortar espacios adicionales
            else -> value.toString()
        }
    }

    fun compareResults(results: List<String>, outputs: List<String>): List<String> {
        val finalResults = mutableListOf<String>()

        if (outputs.size < results.size) {
            for (i in outputs.size until results.size) {
                finalResults.add("Unexpected extra output: ${results[i]}")
            }
        } else if (results.size < outputs.size) {
            for (i in results.size until outputs.size) {
                finalResults.add("Missing expected output: ${outputs[i]}")
            }
        }

        outputs.zip(results) { expected, actual ->
            val normalizedExpected = normalizeValue(expected)
            val normalizedActual = normalizeValue(actual)
            if (normalizedExpected != normalizedActual) {
                finalResults.add("Expected '$normalizedExpected' but got '$normalizedActual'")
            }
        }
        return finalResults
    }
}
