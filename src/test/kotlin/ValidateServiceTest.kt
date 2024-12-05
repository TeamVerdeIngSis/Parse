import com.github.teamverdeingsis.parse.services.ValidateService
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ValidateServiceTest {

    private val validateService = ValidateService()

    @Test
    fun `test validateSnippet should return error messages`() {
        val code = "invalid code"
        val version = "1.0"
        val expectedErrors = listOf("Expected semicolon at line: 1, column: 9")

        val result = validateService.validateSnippet(code, version)
        assertEquals(expectedErrors, result)
    }
}
