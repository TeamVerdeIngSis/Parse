package com.github.teamverdeingsis.parse.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.teamverdeingsis.parse.config.AuthorizationDecoder
import com.github.teamverdeingsis.parse.entity.Conformance
import com.github.teamverdeingsis.parse.entity.SnippetMessage
import com.github.teamverdeingsis.parse.entity.UpdateConformanceRequest
import com.github.teamverdeingsis.parse.services.LinterService
import org.austral.ingsis.redis.RedisStreamConsumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.stream.StreamReceiver
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Component
class LinterRuleConsumer @Autowired constructor(
    redis: ReactiveRedisTemplate<String, String>,
    @Value("\${stream.lintingKey}") streamKey: String,
    @Value("\${groups.linting}") groupId: String,
    private val restTemplate: RestTemplate,
    private val service: LinterService,
    private val objectMapper: ObjectMapper
) : RedisStreamConsumer<String>(streamKey, groupId, redis) {

    private fun reLintSnippet(authorization: String, snippetId: String) {
        try {
            val userId = AuthorizationDecoder.decode(authorization)
            val lintingResults = service.lintSnippet(snippetId, userId)
            val conformance = if (lintingResults.isNotEmpty()) {
                Conformance.NOT_COMPLIANT
            } else {
                Conformance.COMPLIANT
            }

            val url = "http://localhost:8083/updateConformance"
            val requestBody = UpdateConformanceRequest(snippetId, conformance)
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                set("Authorization", authorization)
            }
            val request = HttpEntity(requestBody, headers)
            val response = restTemplate.postForObject(url, request, String::class.java)
        } catch (e: Exception) {
            println("Error relinteando snippet con userId: ${AuthorizationDecoder.decode(authorization)} y snippetId: $snippetId - ${e.message}")
        }
    }

    override fun onMessage(record: ObjectRecord<String, String>) {
        try {
            // Deserializar el mensaje JSON a un objeto SnippetMessage
            val snippetMessage = objectMapper.readValue(record.value, SnippetMessage::class.java)
            val authorization = snippetMessage.userId
            val snippetId = snippetMessage.snippetId

            reLintSnippet(authorization, snippetId)
        } catch (e: Exception) {
            println("Error al procesar el mensaje: ${e.message}")
        }
    }

    override fun options(): StreamReceiver.StreamReceiverOptions<String, ObjectRecord<String, String>> {
        return StreamReceiver.StreamReceiverOptions.builder()
            .targetType(String::class.java)
            .build()
    }
}
