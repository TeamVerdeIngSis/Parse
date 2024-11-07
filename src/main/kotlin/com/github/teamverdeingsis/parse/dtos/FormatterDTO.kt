package com.github.teamverdeingsis.parse.dtos

import java.io.InputStream

data class FormatterDTO (
    val code: InputStream,
    val version: String,
    val rules: List<String>
)