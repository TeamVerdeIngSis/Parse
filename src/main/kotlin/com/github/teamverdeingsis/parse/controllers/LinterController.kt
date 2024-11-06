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
        val code = request.code
        val version = request.version
        val rules = request.rules

        return service.lintSnippet(code, version, rules)
    }
}