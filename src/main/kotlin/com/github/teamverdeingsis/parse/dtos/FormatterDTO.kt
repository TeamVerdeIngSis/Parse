package com.github.teamverdeingsis.parse.dtos

data class FormatterDTO (
    val code: String,
    val version: String,
    val rules: List<String>
)