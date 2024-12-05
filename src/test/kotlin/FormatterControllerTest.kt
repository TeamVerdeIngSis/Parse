import com.github.teamverdeingsis.parse.controllers.FormatterController
import com.github.teamverdeingsis.parse.dtos.FormatterDTO
import com.github.teamverdeingsis.parse.services.FormatterService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class FormatterControllerTest {

    private val formatterService = mock<FormatterService>()
    private val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(FormatterController(formatterService)).build()

    @Test
    fun `test format should return formatted result from service`() {
        val request = FormatterDTO(snippetId = "123", userId = "user123", content = "Snippet")
        val expectedResult = "Formatted Snippet"

        // Simulamos la respuesta del servicio
        whenever(formatterService.formatSnippet("123", "user123")).thenReturn(expectedResult)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/parser/format")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"snippetId": "123", "userId": "user123", "content": "Snippet"}"""),
        )
            .andExpect(status().isOk())
            .andExpect { result ->
                assert(result.response.contentAsString == expectedResult)
            }
    }
}
