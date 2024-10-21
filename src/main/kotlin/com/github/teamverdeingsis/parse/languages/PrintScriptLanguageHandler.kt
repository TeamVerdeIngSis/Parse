package com.github.teamverdeingsis.parse.languages

import com.github.teamverdeingsis.parse.models.results.ExecutionResult
import com.github.teamverdeingsis.parse.models.results.FormattedSnippet
import com.github.teamverdeingsis.parse.models.results.LintingResult
import com.github.teamverdeingsis.parse.models.results.ValidationResult

class PrintScriptLanguageHandler : LanguageHandler {
    override fun lint(code: String, rules: List<String>): LintingResult {
        // linteo usando PrintScript
        return LintingResult(success = true, errors = emptyList())
    }

    override fun format(code: String, rules: List<String>): FormattedSnippet {
        // formateo usando PrintScript
        return FormattedSnippet(formattedCode = code)
    }

    override fun validate(code: String): ValidationResult {
        // validación usando PrintScript
        return ValidationResult(isValid = true, errors = emptyList())
    }

    override fun execute(code: String): ExecutionResult {
        // ejecución usando PrintScript
        return ExecutionResult(output = "Result of execution")
    }
}
