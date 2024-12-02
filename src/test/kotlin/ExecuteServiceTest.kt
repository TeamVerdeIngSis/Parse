// package com.github.teamverdeingsis.parse.services
//
// import org.junit.jupiter.api.BeforeEach
// import org.junit.jupiter.api.Test
// import org.mockito.Mockito
// import org.mockito.kotlin.mock
// import org.junit.jupiter.api.extension.ExtendWith
// import org.mockito.junit.jupiter.MockitoExtension
// import org.springframework.boot.test.context.SpringBootTest
// import kotlin.test.assertEquals
//
// @ExtendWith(MockitoExtension::class)
// @SpringBootTest
// class ExecuteServiceTest {
//
//    private lateinit var executeService: ExecuteService
//    private val assetService: AssetService = mock()
//
//    @BeforeEach
//    fun setUp() {
//        executeService = ExecuteService(assetService)
//    }
//
//    @Test
//    fun `test executeSnippet returns expected result`() {
//        val code = "println('hello');"
//        val version = "1.1"
//
//        Mockito.`when`(assetService.getAsset("snippets", "testSnippet")).thenReturn(code)
//
//        val result = executeService.executeSnippet(code, version)
//
//        assertEquals("['hello']", result)
//    }
//
//    @Test
//    fun `test test method compares expected outputs correctly`() {
//        val snippetId = "testSnippet"
//        val version = "1.1"
//        val inputs = listOf("input1", "input2")
//        val outputs = listOf("output1", "output2")
//
//        Mockito.`when`(assetService.getAsset("snippets", snippetId)).thenReturn("let a:string = 'hello';")
//
//        val results = executeService.test(version, snippetId, inputs, outputs)
//
//        assert(results.isNotEmpty())
//    }
// }
