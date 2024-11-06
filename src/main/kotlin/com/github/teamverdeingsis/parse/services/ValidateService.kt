package com.github.teamverdeingsis.parse.services

import org.springframework.stereotype.Service

@Service
class ValidateService {
    fun validateSnippet(code: String, version: String): String {
        return "hello there"
    }
}