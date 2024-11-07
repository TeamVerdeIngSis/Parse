package com.github.teamverdeingsis.parse.controllers

import com.github.teamverdeingsis.parse.dtos.ExecuteDTO
import com.github.teamverdeingsis.parse.services.ExecuteService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/parser")
class ExecuteController(private val service: ExecuteService) {

    @PostMapping("/execute")
    fun execute(@RequestBody request: ExecuteDTO): String {
        val code = request.code
        val version = request.version
        println("Executing code: $code")
        return service.executeSnippet(code, version)
    }

    @PostMapping("/execute2")
    fun execute(
        @RequestParam("code") file: MultipartFile,
        @RequestParam("version") version: String
    ): String {
        val inputStream = file.inputStream
        return service.executeSnippet2(inputStream, version)
    }
}