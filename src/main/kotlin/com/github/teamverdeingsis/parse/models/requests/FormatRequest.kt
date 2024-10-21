package com.github.teamverdeingsis.parse.models.requests

data class FormatRequest(
    val snippetCode: String,
    val languageVersion: String,
    val formattingRules: List<String>
)
