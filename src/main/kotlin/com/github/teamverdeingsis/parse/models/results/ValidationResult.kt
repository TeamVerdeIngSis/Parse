package com.github.teamverdeingsis.parse.models.results

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<String>
)
