package com.github.teamverdeingsis.parse.models

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<String>
)
