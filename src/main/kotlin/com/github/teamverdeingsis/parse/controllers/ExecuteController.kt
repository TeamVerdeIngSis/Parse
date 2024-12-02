package com.github.teamverdeingsis.parse.controllers

import com.github.teamverdeingsis.parse.dtos.ExecuteDTO
import com.github.teamverdeingsis.parse.dtos.TestDto
import com.github.teamverdeingsis.parse.services.ExecuteService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
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
        println("Executing code: $code")
        return service.executeSnippet(code, version)
    }

    @PostMapping("/test")
    fun test(@RequestBody testDto: TestDto): List<String> {
        println("Received testDto: $testDto") // Log para depuración
        return service.test(testDto.version, testDto.snippetId, testDto.inputs, testDto.outputs)
    }

    private val logger = LoggerFactory.getLogger(ExecuteController::class.java)

    @GetMapping("/hola")
    fun hey(): String {
        logger.info("Executing /hola endpoint")
        return service.hola()
    }
}
