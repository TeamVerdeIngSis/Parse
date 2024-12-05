import com.github.teamverdeingsis.parse.controllers.LinterController
import com.github.teamverdeingsis.parse.entity.SnippetMessage
import com.github.teamverdeingsis.parse.services.LinterService
import linter.LinterError
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class LinterControllerTest {

    private val linterService = mock<LinterService>()
    private val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(LinterController(linterService)).build()

    @Test
    fun `test lint should return linter errors from service`() {
        val request = SnippetMessage(userId = "user123", snippetId = "123")
        val expectedErrors = listOf(LinterError("Could not lint", 1, 1))

        // Simulamos la respuesta del servicio
        whenever(linterService.lintSnippet("user123", "123")).thenReturn(expectedErrors)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/parser/lint")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"userId": "user123", "snippetId": "123"}""")
        )
            .andExpect(status().isOk())
            .andExpect { result ->
                val response = result.response.contentAsString
                // Aquí comparas la respuesta (asegúrate de comparar el JSON adecuado)
                assert(response.contains("Could not lint"))
            }
    }
}
