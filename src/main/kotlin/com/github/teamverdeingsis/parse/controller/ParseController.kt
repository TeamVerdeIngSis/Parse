package com.github.teamverdeingsis.parse.controller

import com.github.teamverdeingsis.parse.models.requests.AnalyzeRequest
import com.github.teamverdeingsis.parse.models.requests.ExecuteRequest
import com.github.teamverdeingsis.parse.models.requests.FormatRequest
import com.github.teamverdeingsis.parse.models.requests.ValidateRequest
import com.github.teamverdeingsis.parse.models.results.ExecutionResult
import com.github.teamverdeingsis.parse.models.results.FormattedSnippet
import com.github.teamverdeingsis.parse.models.results.LintingResult
import com.github.teamverdeingsis.parse.models.results.ValidationResult
import com.github.teamverdeingsis.parse.services.ParserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/parser")
class ParseController(private val parserService: ParserService) {

    @PostMapping("/analyze")
    fun analyzeSnippet(@RequestBody request: AnalyzeRequest): LintingResult {
        return parserService.lintSnippet(request.snippetCode, request.languageVersion, request.lintingRules)
    }

    @PostMapping("/format")
    fun formatSnippet(@RequestBody request: FormatRequest): FormattedSnippet {
        return parserService.formatSnippet(request.snippetCode, request.languageVersion, request.formattingRules)
    }

    @PostMapping("/validate")
    fun validateSnippet(@RequestBody request: ValidateRequest): ValidationResult {
        return parserService.validateSnippet(request.snippetCode, request.languageVersion)
    }

    @PostMapping("/execute")
    fun executeSnippet(@RequestBody request: ExecuteRequest): ExecutionResult {
        return parserService.executeSnippet(request.snippetCode, request.languageVersion)
    }

    @PostMapping("/pong")
    fun pong(): String {
        return "pong"
    }
}
