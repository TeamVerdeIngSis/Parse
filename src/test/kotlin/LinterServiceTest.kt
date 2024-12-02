// package com.github.teamverdeingsis.parse.services
//
// import org.junit.jupiter.api.BeforeEach
// import org.junit.jupiter.api.Test
// import org.mockito.Mockito
// import org.mockito.kotlin.mock
// import org.junit.jupiter.api.extension.ExtendWith
// import org.mockito.junit.jupiter.MockitoExtension
// import org.springframework.boot.test.context.SpringBootTest
// import org.springframework.web.client.RestTemplate
// import kotlin.test.assertTrue
//
// @ExtendWith(MockitoExtension::class)
// @SpringBootTest
// class LinterServiceTest {
//
//    private lateinit var linterService: LinterService
//    private val restTemplate: RestTemplate = mock()
//
//    @BeforeEach
//    fun setUp() {
//        linterService = LinterService(restTemplate)
//    }
//
//    @Test
//    fun `test lintSnippet returns linting errors correctly`() {
//        val snippetId = "testSnippet"
//        val userId = "user123"
//        val code = "let myVar: number = 1;"
//        val rulesJson = "[{\"id\": 1, \"name\": \"snake-case-variables\", \"isActive\": true}]\n"
//
//        Mockito.`when`(restTemplate.getForObject("http://asset-service-infra:8080/v1/asset/snippets/$snippetId", String::class.java))
//            .thenReturn(code)
//        Mockito.`when`(restTemplate.getForObject("http://asset-service-infra:8080/v1/asset/linting/$userId", String::class.java))
//            .thenReturn(rulesJson)
//
//        val lintErrors = linterService.lintSnippet(snippetId, userId)
//
//        assertTrue(lintErrors.isNotEmpty())
//    }
// }
