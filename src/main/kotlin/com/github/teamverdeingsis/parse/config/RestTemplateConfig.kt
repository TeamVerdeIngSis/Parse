package com.github.teamverdeingsis.parse.config

import com.github.teamverdeingsis.parse.interceptor.NewRelicInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.interceptors.add(NewRelicInterceptor())
        return restTemplate
    }
}
