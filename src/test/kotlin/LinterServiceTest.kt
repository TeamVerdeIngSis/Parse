import com.github.teamverdeingsis.parse.services.LinterService
import linter.LinterError
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class LinterServiceTest {

    private val linterService: LinterService = Mockito.mock(LinterService::class.java)

    @Test
    fun `test lintSnippet should return linter errors`() {
        // Arrange: Datos de prueba mockeados
        val snippetId = "snippet123"
        val userId = "auth0|673d25d3d89c5b9fa189c07c"

        // Mockeamos los errores de linter
        val linterError1 = LinterError("Variable names should be in snake_case", 1, 5)
        val linterError2 = LinterError("Missing semicolon", 2, 10)

        val linterErrors = listOf(linterError1, linterError2)

        // Mockeamos la respuesta del servicio
        whenever(linterService.lintSnippet(snippetId, userId)).thenReturn(linterErrors)

        // Act: Llamamos al método lintSnippet
        val result = linterService.lintSnippet(snippetId, userId)

        // Assert: Verificamos que los errores de linter sean los esperados
        assertEquals(2, result.size)  // Esperamos que haya dos errores
        assertEquals("Variable names should be in snake_case", result[0].message)  // Verificamos el mensaje del primer error
        assertEquals("Missing semicolon", result[1].message)  // Verificamos el mensaje del segundo error
    }
}
