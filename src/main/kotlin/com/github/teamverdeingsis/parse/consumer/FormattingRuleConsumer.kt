package com.github.teamverdeingsis.parse.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.teamverdeingsis.parse.config.AuthorizationDecoder
import com.github.teamverdeingsis.parse.entity.SnippetMessage
import com.github.teamverdeingsis.parse.services.FormatterService
import org.austral.ingsis.redis.RedisStreamConsumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.stream.StreamReceiver
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class FormattingRuleConsumer @Autowired constructor(
    redis: ReactiveRedisTemplate<String, String>,
    @Value("\${stream.formattingKey}") streamKey: String,
    @Value("\${groups.formatting}") groupId: String,
    private val service: FormatterService,
    private val objectMapper: ObjectMapper
) : RedisStreamConsumer<String>(streamKey, groupId, redis) {

    override fun onMessage(record: ObjectRecord<String, String>) {
        try {
            println("Checkpoint 1, formatting consumer")
            val snippetMessage = objectMapper.readValue(record.value, SnippetMessage::class.java)
            val authorization = snippetMessage.userId
            val userId = AuthorizationDecoder.decode(authorization)
            val snippetId = snippetMessage.snippetId
            println("Checkpoint 2, Formateando snippet $snippetId con las reglas del usuario $userId")
            val formattedContent = service.formatSnippet(snippetId,userId)
            println("Snippet formateado: $formattedContent")
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
