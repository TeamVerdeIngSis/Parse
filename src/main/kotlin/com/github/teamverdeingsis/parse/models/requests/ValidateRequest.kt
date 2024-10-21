package com.github.teamverdeingsis.parse.models.requests

data class ValidateRequest(
    val snippetCode: String,
    val languageVersion: String
)
