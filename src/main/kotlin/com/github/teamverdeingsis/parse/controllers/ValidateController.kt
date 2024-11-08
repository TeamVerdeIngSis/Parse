package com.github.teamverdeingsis.parse.controllers

import com.github.teamverdeingsis.parse.dtos.ValidateDTO
import com.github.teamverdeingsis.parse.services.ValidateService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/parser")
class ValidateController(private val service: ValidateService) {

    @PostMapping("/validate")
    fun validate(@RequestBody request: ValidateDTO): String {
        return service.validateSnippet(request.code, request.version)
    }

}