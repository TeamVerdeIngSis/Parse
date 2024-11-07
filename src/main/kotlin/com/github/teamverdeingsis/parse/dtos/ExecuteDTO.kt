package com.github.teamverdeingsis.parse.dtos

import java.io.InputStream

data class ExecuteDTO(
    val code: InputStream,
    val version: String
)
