package com.example.parser.models

data class AnalyzeRequest(
    val snippetCode: String,
    val languageVersion: String,
    val lintingRules: List<String>
)
