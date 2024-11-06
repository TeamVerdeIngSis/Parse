package com.github.teamverdeingsis.parse.services

import org.springframework.stereotype.Service

@Service
class LinterService {
    fun lintSnippet(code: String, version: String, rules: String): List<String> {
        return listOf("hello", "there")
    }
}