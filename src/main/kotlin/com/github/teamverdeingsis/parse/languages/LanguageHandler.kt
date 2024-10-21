package com.github.teamverdeingsis.parse.languages

import com.github.teamverdeingsis.parse.models.results.ExecutionResult
import com.github.teamverdeingsis.parse.models.results.FormattedSnippet
import com.github.teamverdeingsis.parse.models.results.LintingResult
import com.github.teamverdeingsis.parse.models.results.ValidationResult


interface LanguageHandler {
    fun lint(code: String, rules: List<String>): LintingResult
    fun format(code: String, rules: List<String>): FormattedSnippet
    fun validate(code: String): ValidationResult
    fun execute(code: String): ExecutionResult
}
