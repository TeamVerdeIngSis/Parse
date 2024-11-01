package com.github.teamverdeingsis.parse.languages

import com.github.teamverdeingsis.parse.models.results.ExecutionResult
import com.github.teamverdeingsis.parse.models.results.FormattedSnippet
import com.github.teamverdeingsis.parse.models.results.LintingResult
import com.github.teamverdeingsis.parse.models.results.ValidationResult
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class PrintScriptLanguageHandler : LanguageHandler {

    private fun captureOutputOf(command: () -> Unit): String {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val oldOut = System.out
        try {
            System.setOut(printStream)
            command()
        } finally {
            System.setOut(oldOut)
        }
        return outputStream.toString()
    }

    override fun lint(code: String, rules: List<String>): LintingResult {
//        val output = captureOutputOf {
//            val analyzer = AnalyzingCommand()
//            analyzer.run()
//        }
        return LintingResult(success = false, errors = listOf())
    }

    override fun format(code: String, rules: List<String>): FormattedSnippet {
//        val output = captureOutputOf {
//            val formatter = FormattingCommand()
//            formatter.run()
//        }
        return FormattedSnippet(formattedCode = "")
    }

    override fun validate(code: String): ValidationResult {
//        val output = captureOutputOf {
//            val validator = ValidationCommand()
//            validator.run()
//        }
        return ValidationResult(isValid = true, ast = "")
    }

    override fun execute(code: String): ExecutionResult {
//        val output = captureOutputOf {
//            val executor = ExecutionCommand()
//            executor.run()
//        }
        return ExecutionResult(output = "")
    }
}