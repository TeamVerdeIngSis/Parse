import com.github.teamverdeingsis.parse.controllers.ValidateController
import com.github.teamverdeingsis.parse.dtos.ValidateDTO
import com.github.teamverdeingsis.parse.services.ValidateService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class ValidateControllerTest {

    private val validateService = mock<ValidateService>()
    private val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(ValidateController(validateService)).build()

    @Test
    fun `test validate should return validation errors from service`() {
        val request = ValidateDTO(code = "invalid code", version = "1.0")
        val expectedErrors = listOf("Syntax error in code", "Invalid version format")

        // Simulamos la respuesta del servicio
        whenever(validateService.validateSnippet("invalid code", "1.0")).thenReturn(expectedErrors)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/parser/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"code": "invalid code", "version": "1.0"}""")
        )
            .andExpect(status().isOk())
            .andExpect { result ->
                val response = result.response.contentAsString
                // Aquí comparas la respuesta
                assert(response.contains("Syntax error in code"))
            }
    }
}
