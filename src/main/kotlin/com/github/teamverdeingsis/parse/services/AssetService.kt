package com.github.teamverdeingsis.parse.services

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class AssetService(private val restTemplate: RestTemplate) {

    fun getAsset(directory: String, snippetId: String): String? {
        val assetServiceUrl = "http://localhost:8080/v1/asset/$directory/$snippetId"
        return try {
            restTemplate.getForObject(assetServiceUrl, String::class.java)
        } catch (e: Exception) {
            ""
        }
    }
}