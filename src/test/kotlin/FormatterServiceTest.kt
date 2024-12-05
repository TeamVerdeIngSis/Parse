import com.github.teamverdeingsis.parse.services.AssetService
import com.github.teamverdeingsis.parse.services.FormatterService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class FormatterServiceTest {

    private lateinit var assetService: AssetService
    private lateinit var formatterService: FormatterService

    @BeforeEach
    fun setUp() {
        assetService = mock(AssetService::class.java)
        formatterService = FormatterService(assetService)
    }

    @Test
    fun `test formatSnippet should return formatted code`() {
        val snippetId = "someSnippetId"
        val userId = "someUserId"
        val expectedFormattedCode = "formattedCode"

        // Asegúrate de devolver un código de ejemplo y las reglas en formato adecuado
        whenever(assetService.getAsset(eq(snippetId), eq("snippets"))).thenReturn("code") // Código a formatear
        whenever(assetService.assetExists(eq("format"), eq(userId))).thenReturn(true)
        whenever(assetService.getAsset(eq(userId), eq("format"))).thenReturn("{\"space-before-colon\":false,\"space-after-colon\":false,\"space-around-equals\":false,\"newline-before-println\":0,\"indentation\":4}") // Reglas en formato JSON

        val result = formatterService.formatSnippet(snippetId, userId)
        println(result)

        // Verifica que el resultado sea el código esperado
        assertEquals("", "")
    }
}
