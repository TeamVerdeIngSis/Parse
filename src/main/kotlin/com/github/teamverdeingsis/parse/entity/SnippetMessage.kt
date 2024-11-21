package com.github.teamverdeingsis.parse.entity

data class SnippetMessage(
    val userId: String,
    val snippetId: String
)

data class Rule(
    val id: String,
    val name: String,
    val isActive: Boolean,
    val value: String? = null
)
