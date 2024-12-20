package com.github.teamverdeingsis.parse.services

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AssetService(private val restTemplate: RestTemplate) {
    public fun addAsset(content: String, directory: String, id: String): ResponseEntity<String> {
        println("Voy a agregar un asset con $content, $directory y $id")
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$id"
        restTemplate.put(assetServiceUrl, content, String::class.java)
        return ResponseEntity.ok().body("Asset with ID $id added")
    }

    public fun updateAsset(assetId: String, directory: String, content: String): ResponseEntity<String> {
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$assetId"

        try {
            restTemplate.put(assetServiceUrl, content, String::class.java)
            return ResponseEntity.ok("Asset with ID $assetId updated")
        } catch (e: Exception) {
            throw RuntimeException("Asset with ID $assetId not found")
        }
    }

    public fun deleteAsset(snippetId: String, directory: String): ResponseEntity<String> {
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$snippetId"
        try {
            restTemplate.delete(assetServiceUrl)
            return ResponseEntity.ok().body("Asset with ID $snippetId deleted")
        } catch (e: Exception) {
            throw RuntimeException("Asset with ID $snippetId not found")
        }
    }
    public fun getAsset(snippetId: String, directory: String): String? {
        println("Voy a obtener un asset con $snippetId y $directory")
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$snippetId"
        return try {
            restTemplate.getForObject(assetServiceUrl, String::class.java)
        } catch (e: Exception) {
            ""
        }
    }
    fun assetExists(directory: String, snippetId: String): Boolean {
        println("chequeando que el asset con dir $directory y id $snippetId exista")
        val assetServiceUrl = "http://asset-service-infra:8080/v1/asset/$directory/$snippetId"
        try {
            restTemplate.getForObject(assetServiceUrl, String::class.java)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
