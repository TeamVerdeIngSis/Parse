package com.github.teamverdeingsis.parse.config

import com.github.teamverdeingsis.parse.entity.SnippetMessage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun reactiveRedisTemplate(connectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, SnippetMessage> {
        // Serializadores
        val keySerializer: RedisSerializer<String> = StringRedisSerializer()
        val valueSerializer: RedisSerializer<SnippetMessage> = GenericJackson2JsonRedisSerializer() as RedisSerializer<SnippetMessage>

        // Crear contexto de serialización con pares correctos
        val serializationContext = RedisSerializationContext
            .newSerializationContext<String, SnippetMessage>(keySerializer)
            .key(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer)) // Clave
            .value(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer)) // Valor
            .build()

        // Crear ReactiveRedisTemplate
        return ReactiveRedisTemplate(connectionFactory, serializationContext)
    }
}
