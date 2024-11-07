package com.github.teamverdeingsis.parse.dtos

import java.io.InputStream

data class ValidateDTO(
    val code: InputStream,
    val version: String
)
