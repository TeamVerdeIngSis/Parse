//package com.github.teamverdeingsis.parse.services
//
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.mockito.Mockito
//import org.mockito.kotlin.mock
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.junit.jupiter.MockitoExtension
//import org.springframework.boot.test.context.SpringBootTest
//import kotlin.test.assertEquals
//
//@ExtendWith(MockitoExtension::class)
//@SpringBootTest
//class FormatterServiceTest {
//
//    private lateinit var formatterService: FormatterService
//    private val assetService: AssetService = mock()
//
//    @BeforeEach
//    fun setUp() {
//        formatterService = FormatterService(assetService)
//    }
//
//    @Test
//    fun `test formatSnippet formats correctly`() {
//        val snippetId = "testSnippet"
//        val userId = "user123"
//        val code = "let myVar : number =5;"
//        Mockito.`when`(assetService.getAsset("snippets", snippetId)).thenReturn(code)
//        Mockito.`when`(assetService.getAsset("format", userId)).thenReturn("[]")  // Default formatting rules
//
//        val formattedCode = formatterService.formatSnippet(snippetId, userId)
//
//        assertEquals("let myVar:number = 5.0;", formattedCode)  // Assuming formatting doesn't change this code
//    }
//}
