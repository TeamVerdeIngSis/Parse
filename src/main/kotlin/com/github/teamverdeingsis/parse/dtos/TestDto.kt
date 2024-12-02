package com.github.teamverdeingsis.parse.dtos

data class TestDto(
    val version: String = "1.1",
    val snippetId: String,
    val inputs: List<String>,
    val outputs: List<String>,
)
