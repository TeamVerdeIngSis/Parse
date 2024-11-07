package com.github.teamverdeingsis.parse.controllers

import com.github.teamverdeingsis.parse.dtos.FormatterDTO
import com.github.teamverdeingsis.parse.services.FormatterService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/parser")
class FormatterController(private val service: FormatterService) {

    @PostMapping("/format")
    fun format(@RequestBody request: FormatterDTO): String {
        val code = request.code
        val version = request.version
        val rules = request.rules

        return service.formatSnippet(code, version, rules.toString())
    }
}