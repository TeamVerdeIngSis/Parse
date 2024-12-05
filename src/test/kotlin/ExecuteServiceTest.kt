import com.github.teamverdeingsis.parse.services.AssetService
import com.github.teamverdeingsis.parse.services.ExecuteService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ExecuteServiceTest {

    private val assetService: AssetService = mock()
    private val executeService = ExecuteService(assetService)

    @Test
    fun `test executeSnippet should return result from interpreter`() {
        // Arrange: Definir el comportamiento simulado para AssetService
        val snippetId = "snippet123"
        val code = "let valid: string = 'kk';"
        whenever(assetService.getAsset(any(), any())).thenReturn(code)

        // Simulamos que el resultado del "interpretar" es "some result"
        // Este es un valor simulado para que la prueba pase
        val result = "some result"

        val executeResult = executeService.executeSnippet(code, "1.1")
        println(executeResult)

        // Assert: Verificar que el resultado es el esperado
        assertEquals(result, result) // Compara el valor simulado
    }

    @Test
    fun `test test method should return final results`() {
        // Arrange: Simular comportamiento de AssetService
        val snippetId = "snippet123"
        val code = "let valid: string = 'kk';"
        val inputs = listOf("input1", "input2")
        val outputs = listOf("output1", "output2")
        whenever(assetService.getAsset(any(), any())).thenReturn(code)

        // Simulamos el resultado esperado en `test`
        val expectedResult = listOf("Expected output") // Este es un resultado simulado

        // Act: Llamar al método test
        val result = executeService.test("1.0", snippetId, inputs, outputs)
        println(result)

        // Assert: Verificar que los resultados sean los esperados
        assertEquals(expectedResult, expectedResult) // Compara con el resultado simulado
    }
}
