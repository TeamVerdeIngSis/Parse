package com.github.teamverdeingsis.parse.controllers

import com.github.teamverdeingsis.parse.entity.SnippetMessage
import com.github.teamverdeingsis.parse.services.LinterService
import linter.LinterError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/parser")
class LinterController(private val service: LinterService) {

    @PostMapping("/lint")
    fun lint(@RequestBody request: SnippetMessage): List<LinterError> {
        println("AAA")
        try {
            val response = service.lintSnippet(request.userId, request.snippetId)
        return response
        } catch (e: Exception) {
            println("Error occurred while linting: ${e.message}")
            return listOf(LinterError("couldnt lint",0,0))
        }
    }
}