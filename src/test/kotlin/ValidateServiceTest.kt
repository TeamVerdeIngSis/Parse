// package com.github.teamverdeingsis.parse.services
//
// import org.junit.jupiter.api.BeforeEach
// import org.junit.jupiter.api.Test
// import org.mockito.kotlin.mock
// import org.junit.jupiter.api.extension.ExtendWith
// import org.mockito.junit.jupiter.MockitoExtension
// import org.springframework.boot.test.context.SpringBootTest
// import kotlin.test.assertEquals
// import kotlin.test.assertTrue
//
// @ExtendWith(MockitoExtension::class)
// @SpringBootTest
// class ValidateServiceTest {
//
//    private lateinit var validateService: ValidateService
//
//    @BeforeEach
//    fun setUp() {
//        validateService = ValidateService()
//    }
//
//    @Test
//    fun `test validateSnippet returns errors correctly`() {
//        val code = "let x: number = 5;"
//        val version = "1.1"
//
//        val validationErrors = validateService.validateSnippet(code, version)
//
//        assertTrue(validationErrors.size == 0)  // Assuming no syntax errors in the given code
//    }
// }
