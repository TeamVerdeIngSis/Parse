package com.github.teamverdeingsis.parse.dtos

data class TestDto(
    val version: String,
    val snippetId: String,
    val inputs: List<String>,
    val outputs: List<String>
)