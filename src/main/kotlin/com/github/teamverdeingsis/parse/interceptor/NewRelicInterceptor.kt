package com.github.teamverdeingsis.parse.interceptor


import com.github.teamverdeingsis.snippets.server.CorrelationIdFilter
import org.slf4j.MDC
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import java.io.IOException
import java.util.*
import com.newrelic.api.agent.Trace


class NewRelicInterceptor : ClientHttpRequestInterceptor {

    @Trace(dispatcher = true)
    @Throws(IOException::class)
    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {

        val id = MDC.get(CorrelationIdFilter.CORRELATION_ID_KEY) ?: UUID.randomUUID().toString()
        request.headers.add(CorrelationIdFilter.CORRELATION_ID_HEADER, id)
        println("BBB")
        println(id)
        return execution.execute(request, body)
    }
}