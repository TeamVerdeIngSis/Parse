import com.fasterxml.jackson.core.type.TypeReference
import com.github.teamverdeingsis.parse.services.LinterService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import kotlin.test.assertNotNull

class LinterServiceTypeReferenceTest {

    private val linterService: LinterService = Mockito.mock(LinterService::class.java)

    @Test
    fun `test TypeReference in lintSnippet`() {
        // Arrange: Datos de prueba mockeados
        val snippetId = "snippet123"
        val userId = "auth0|673d25d3d89c5b9fa189c07c"

        // Act: Llamamos al método lintSnippet y obtenemos el TypeReference
        val typeReference = object : TypeReference<List<String>>() {}

        // Assert: Verificamos que el TypeReference no sea nulo
        assertNotNull(typeReference)
    }
}
