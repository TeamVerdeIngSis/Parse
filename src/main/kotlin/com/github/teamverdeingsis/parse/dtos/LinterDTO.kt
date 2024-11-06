package com.github.teamverdeingsis.parse.dtos

data class LinterDTO(
    val code: String,
    val version: String,
    val rules: List<String>
)
