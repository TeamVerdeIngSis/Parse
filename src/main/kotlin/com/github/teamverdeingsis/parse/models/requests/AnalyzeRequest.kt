package com.github.teamverdeingsis.parse.models.requests

data class AnalyzeRequest(
    val snippetCode: String,
    val languageVersion: String,
    val lintingRules: List<String>
)
