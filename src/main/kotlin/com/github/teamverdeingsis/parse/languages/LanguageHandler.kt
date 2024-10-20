package com.github.teamverdeingsis.parse.languages

import com.github.teamverdeingsis.parse.models.*

interface LanguageHandler {
    fun lint(code: String, rules: List<String>): LintingResult
    fun format(code: String, rules: List<String>): FormattedSnippet
    fun validate(code: String): ValidationResult
    fun execute(code: String): ExecutionResult
}
