import com.github.teamverdeingsis.parse.ParseApplication
import com.github.teamverdeingsis.parse.services.AssetService
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.*
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)
@SpringBootTest(classes = [ParseApplication::class])
class AssetServiceTest {

    @Mock
    private lateinit var restTemplate: RestTemplate

    private lateinit var assetService: AssetService

    @Test
    fun `should call addAsset and return ResponseEntity`() {
        val content = "test content"
        val directory = "testDirectory"
        val id = "123"
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$id"

        doNothing().`when`(restTemplate).put(assetServiceUrl, content, String::class.java)

        assetService = AssetService(restTemplate)

        val result = assetService.addAsset(content, directory, id)

        assert(result.statusCode.value() == 200)
        verify(restTemplate).put(assetServiceUrl, content, String::class.java)
    }

    @Test
    fun `should call updateAsset and return ResponseEntity`() {
        val assetId = "123"
        val directory = "testDirectory"
        val content = "updated content"
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$assetId"

        doNothing().`when`(restTemplate).put(assetServiceUrl, content, String::class.java)

        assetService = AssetService(restTemplate)

        val result = assetService.updateAsset(assetId, directory, content)

        assert(result.statusCode.value() == 200)
        assert(result.body == "Asset with ID $assetId updated")
        verify(restTemplate).put(assetServiceUrl, content, String::class.java)
    }

    @Test
    fun `should throw exception when updating non-existent asset`() {
        val assetId = "999"
        val directory = "testDirectory"
        val content = "nonexistent content"
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$assetId"

        whenever(restTemplate.put(eq(assetServiceUrl), eq(content), eq(String::class.java)))
            .thenThrow(RuntimeException("Asset with ID $assetId not found"))

        assetService = AssetService(restTemplate)

        try {
            assetService.updateAsset(assetId, directory, content)
        } catch (e: Exception) {
            assert(e is RuntimeException)
            assert(e.message == "Asset with ID $assetId not found")
        }
    }

    @Test
    fun `should call deleteAsset and return ResponseEntity`() {
        val snippetId = "123"
        val directory = "testDirectory"
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$snippetId"

        doNothing().`when`(restTemplate).delete(assetServiceUrl)

        assetService = AssetService(restTemplate)

        val result = assetService.deleteAsset(snippetId, directory)

        assert(result.statusCode.value() == 200)
        assert(result.body == "Asset with ID $snippetId deleted")
        verify(restTemplate).delete(assetServiceUrl)
    }

    @Test
    fun `should throw exception when deleting non-existent asset`() {
        val snippetId = "999"
        val directory = "testDirectory"
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$snippetId"

        whenever(restTemplate.delete(assetServiceUrl)).thenThrow(RuntimeException("Asset with ID $snippetId not found"))

        assetService = AssetService(restTemplate)

        try {
            assetService.deleteAsset(snippetId, directory)
        } catch (e: Exception) {
            assert(e is RuntimeException)
            assert(e.message == "Asset with ID $snippetId not found")
        }
    }

    @Test
    fun `should call getAsset and return asset content`() {
        val snippetId = "123"
        val directory = "testDirectory"
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$snippetId"
        val mockResponse = "Asset content"

        whenever(restTemplate.getForObject(assetServiceUrl, String::class.java)).thenReturn(mockResponse)

        assetService = AssetService(restTemplate)

        val result = assetService.getAsset(directory, snippetId)

        assert(result == mockResponse)
        verify(restTemplate).getForObject(assetServiceUrl, String::class.java)
    }

    @Test
    fun `should return empty string when asset not found in getAsset`() {
        val snippetId = "999"
        val directory = "testDirectory"
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$snippetId"

        // Cambiado Exception() por RuntimeException()
        whenever(restTemplate.getForObject(assetServiceUrl, String::class.java)).thenThrow(RuntimeException())

        assetService = AssetService(restTemplate)

        val result = assetService.getAsset(directory, snippetId)

        assert(result == "")
        verify(restTemplate).getForObject(assetServiceUrl, String::class.java)
    }

    @Test
    fun `should return true if asset exists`() {
        val snippetId = "123"
        val directory = "testDirectory"
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$snippetId"

        whenever(restTemplate.getForObject(assetServiceUrl, String::class.java)).thenReturn("some content")

        assetService = AssetService(restTemplate)

        val result = assetService.assetExists(directory, snippetId)

        assert(result)
        verify(restTemplate).getForObject(assetServiceUrl, String::class.java)
    }

    @Test
    fun `should return false if asset does not exist`() {
        val snippetId = "999"
        val directory = "testDirectory"
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$snippetId"

        // Cambiado Exception() por RuntimeException()
        whenever(restTemplate.getForObject(assetServiceUrl, String::class.java)).thenThrow(RuntimeException())

        assetService = AssetService(restTemplate)

        val result = assetService.assetExists(directory, snippetId)

        assert(!result)
        verify(restTemplate).getForObject(assetServiceUrl, String::class.java)
    }
}
