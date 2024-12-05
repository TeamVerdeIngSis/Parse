import com.github.teamverdeingsis.parse.controllers.ExecuteController
import com.github.teamverdeingsis.parse.dtos.ExecuteDTO
import com.github.teamverdeingsis.parse.services.ExecuteService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class ExecuteControllerTest {

    private val executeService = mock<ExecuteService>()
    private val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(ExecuteController(executeService)).build()

    @Test
    fun `test execute should return result from service`() {
        val request = ExecuteDTO(code = "valid code", version = "1.0")
        val expectedResult = "Execution Result"

        // Simulamos la respuesta del servicio
        whenever(executeService.executeSnippet("valid code", "1.0")).thenReturn(expectedResult)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/parser/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"code": "valid code", "version": "1.0"}"""),
        )
            .andExpect(status().isOk())
            .andExpect { result ->
                assert(result.response.contentAsString == expectedResult)
            }
    }
}
