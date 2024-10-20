package com.example.parser.models

data class FormatRequest(
    val snippetCode: String,
    val languageVersion: String,
    val formattingRules: List<String>
)
