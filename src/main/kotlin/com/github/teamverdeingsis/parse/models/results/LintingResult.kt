package com.github.teamverdeingsis.parse.models

data class LintingResult(
    val success: Boolean,
    val errors: List<String>
)
