package com.github.teamverdeingsis.parse.services

import org.springframework.stereotype.Service

@Service
class ExecuteService {
    fun executeSnippet(code: String, version: String): String {
        return "hello there"
    }
}