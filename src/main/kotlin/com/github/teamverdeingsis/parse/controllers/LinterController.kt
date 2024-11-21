package com.github.teamverdeingsis.parse.controllers

import com.github.teamverdeingsis.parse.dtos.LinterDTO
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
    fun lint(@RequestBody request: LinterDTO): List<LinterError> {
        println("llegue al linterController con estos valores ${request.snippetId} y ${request.userId}")
        val response = service.lintSnippet(request.snippetId, request.userId)
        println("OOOOOOOOOOOOOOOOOOOOOOOO")
        println(response)
        return response
    }
}