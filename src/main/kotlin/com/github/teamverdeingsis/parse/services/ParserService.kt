package com.github.teamverdeingsis.parse.services

import com.github.teamverdeingsis.parse.languages.LanguageHandler
import com.github.teamverdeingsis.parse.languages.PrintScriptLanguageHandler
import com.github.teamverdeingsis.parse.models.results.ExecutionResult
import com.github.teamverdeingsis.parse.models.results.FormattedSnippet
import com.github.teamverdeingsis.parse.models.results.LintingResult
import com.github.teamverdeingsis.parse.models.results.ValidationResult
import org.springframework.stereotype.Service

@Service
class ParserService {

    private val languageHandlers = mapOf<String, LanguageHandler>(
        "PrintScript" to PrintScriptLanguageHandler(),
    )

    fun getLanguageHandler(languageVersion: String): LanguageHandler {
        return languageHandlers[languageVersion] ?: throw UnsupportedOperationException("Language version not supported")
    }

    fun lintSnippet(snippetCode: String, languageVersion: String, lintingRules: List<String>): LintingResult {
        val handler = getLanguageHandler(languageVersion)
        return handler.lint(snippetCode, lintingRules)
    }

    fun formatSnippet(snippetCode: String, languageVersion: String, formattingRules: List<String>): FormattedSnippet {
        val handler = getLanguageHandler(languageVersion)
        return handler.format(snippetCode, formattingRules)
    }

    fun validateSnippet(snippetCode: String, languageVersion: String): ValidationResult {
        val handler = getLanguageHandler(languageVersion)
        return handler.validate(snippetCode)
    }

    fun executeSnippet(snippetCode: String, languageVersion: String): ExecutionResult {
        val handler = getLanguageHandler(languageVersion)
        return handler.execute(snippetCode)
    }
}
