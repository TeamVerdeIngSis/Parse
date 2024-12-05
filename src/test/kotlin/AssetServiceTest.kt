import com.github.teamverdeingsis.parse.services.AssetService
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.eq
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import kotlin.test.assertEquals

class AssetServiceTest {

    private val restTemplate = mock(RestTemplate::class.java)
    private val assetService = AssetService(restTemplate)

    @Test
    fun `test addAsset should return success message`() {
        val content = "Some content"
        val directory = "someDir"
        val id = "assetId123"
        val expectedResponse = "Asset with ID $id added"

        // Cambiar 'when' por 'doNothing' para los métodos void
        doNothing().`when`(restTemplate).put(anyString(), eq(content), eq(String::class.java))

        val response: ResponseEntity<String> = assetService.addAsset(content, directory, id)
        assertEquals(expectedResponse, response.body)
    }

    @Test
    fun `test updateAsset should return success message`() {
        val assetId = "assetId123"
        val directory = "someDir"
        val content = "Updated content"
        val expectedResponse = "Asset with ID $assetId updated"

        // Cambiar 'when' por 'doNothing' para los métodos void
        doNothing().`when`(restTemplate).put(anyString(), eq(content), eq(String::class.java))

        val response: ResponseEntity<String> = assetService.updateAsset(assetId, directory, content)
        assertEquals(expectedResponse, response.body)
    }

    @Test
    fun `test deleteAsset should return success message`() {
        val snippetId = "assetId123"
        val directory = "someDir"
        val expectedResponse = "Asset with ID $snippetId deleted"

        // Cambiar 'when' por 'doNothing' para los métodos void
        doNothing().`when`(restTemplate).delete(anyString())

        val response: ResponseEntity<String> = assetService.deleteAsset(snippetId, directory)
        assertEquals(expectedResponse, response.body)
    }
}
