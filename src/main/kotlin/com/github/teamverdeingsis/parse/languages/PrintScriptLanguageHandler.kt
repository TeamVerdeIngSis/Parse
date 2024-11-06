package com.github.teamverdeingsis.parse.languages

import com.github.teamverdeingsis.parse.models.results.ExecutionResult
import com.github.teamverdeingsis.parse.models.results.FormattedSnippet
import com.github.teamverdeingsis.parse.models.results.LintingResult
import com.github.teamverdeingsis.parse.models.results.ValidationResult
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class PrintScriptLanguageHandler : LanguageHandler {
    override fun lint(code: String, rules: List<String>): LintingResult {
        TODO("Not yet implemented")
    }

    override fun format(code: String, rules: List<String>): FormattedSnippet {
        TODO("Not yet implemented")
    }

    override fun validate(code: String): ValidationResult {
        TODO("Not yet implemented")
    }

    override fun execute(code: String): ExecutionResult {
        TODO("Not yet implemented")
    }


}