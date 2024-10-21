package com.github.teamverdeingsis.parse.models.results

data class LintingResult(
    val success: Boolean,
    val errors: List<String>
)
