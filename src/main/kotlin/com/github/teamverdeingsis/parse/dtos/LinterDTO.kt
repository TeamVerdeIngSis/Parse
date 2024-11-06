package com.github.teamverdeingsis.parse.dtos

import java.io.InputStream

data class LinterDTO(
    val code: InputStream,
    val version: String
)
