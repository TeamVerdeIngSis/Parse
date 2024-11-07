package com.github.teamverdeingsis.parse.controllers

import com.github.teamverdeingsis.parse.dtos.ExecuteDTO
import com.github.teamverdeingsis.parse.services.ExecuteService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/parser")
class ExecuteController(private val service: ExecuteService) {

    @PostMapping("/execute")
    fun execute(@RequestBody request: ExecuteDTO): String {
        val code = request.code
        val version = request.version

        return service.executeSnippet(code, version)
    }
}