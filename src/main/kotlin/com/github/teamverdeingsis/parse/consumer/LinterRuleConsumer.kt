package com.github.teamverdeingsis.parse.consumer

import com.github.teamverdeingsis.parse.controllers.LinterController
import com.github.teamverdeingsis.parse.dtos.LinterDTO
import com.github.teamverdeingsis.parse.entity.SnippetMessage
import com.github.teamverdeingsis.parse.services.LinterService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import reactor.core.scheduler.Schedulers

@Component
class LinterRuleConsumer(
    @Value("\${stream.linter.key}") private val streamKey: String,
    private val redisTemplate: ReactiveRedisTemplate<String, SnippetMessage>,
    private val restTemplate: RestTemplate
) {

    init {
        startConsumer()
    }

    /**
     * Inicia el consumidor que se suscribe al stream Redis.
     */
    private fun startConsumer() {
        redisTemplate.opsForStream<String, SnippetMessage>()
            .read(
                SnippetMessage::class.java, // Tipo esperado para los mensajes
                StreamOffset.latest(streamKey) // Comenzar desde los mensajes más recientes
            )
            .doOnNext { record ->
                try {
                    processMessage(record.value)
                } catch (e: Exception) {
                    println("Error procesando mensaje: ${e.message}")
                }
            }
            .onErrorContinue { e, _ ->
                println("Error en el stream consumer: ${e.message}")
            }
            .subscribeOn(Schedulers.boundedElastic()) // Ejecutar en un scheduler dedicado
            .subscribe() // Suscribirse para procesar mensajes continuamente
    }

    /**
     * Procesa un mensaje recibido desde el stream.
     * @param message Mensaje recibido del stream.
     */
    private fun processMessage(message: SnippetMessage) {
        println("Processing SnippetMessage: $message")
        reLintSnippet(message.userId, message.snippetId)
    }

    /**
     * Relintea un snippet específico según el userId y snippetId.
     * @param userId ID del usuario al que pertenece el snippet.
     * @param snippetId ID del snippet a relintear.
     */
    private fun reLintSnippet(userId: String, snippetId: String) {
        try {
            println("Relinteando snippet con userId: $userId y snippetId: $snippetId")
            LinterController(LinterService(restTemplate)).lint(LinterDTO( userId, snippetId))

        } catch (e: Exception) {
            println("Error relinteando snippet con userId: $userId y snippetId: $snippetId - ${e.message}")
        }
    }
}
