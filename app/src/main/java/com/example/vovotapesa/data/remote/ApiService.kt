package com.example.vovotapesa.data.remote

import com.example.vovotapesa.data.remote.dto.AuthLogin
import com.example.vovotapesa.data.remote.dto.AuthRegister
import com.example.vovotapesa.data.remote.dto.AuthResponse
import com.example.vovotapesa.data.remote.dto.ProfileResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*

class ApiService(private val client: HttpClient) {
    val baseUrl = "https://pesa.vovota.bi/api"

//    suspend fun register(request: AuthRegister) {
//        val response: HttpResponse = client.post("$baseUrl/user/") {
//            contentType(ContentType.Application.Json)
//            setBody(request)
//        }
//        if (!response.status.isSuccess()) {
//            throw Exception("Registration failed with status: ${response.status}")
//        }
//    }

    suspend fun register(request: AuthRegister) {
        val response: HttpResponse = client.post("$baseUrl/user/") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (!response.status.isSuccess()) {
            // Read the error body for more details
            val errorBody = response.bodyAsText()
            // Log or throw with error details
            throw Exception("Registration failed with status: ${response.status}. Details: $errorBody")
        }
    }




    suspend fun login(request: AuthLogin): AuthResponse {
        return client.post("$baseUrl/token/") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun getProfile(token: String): List<ProfileResponse> {
        return client.get("$baseUrl/profile/") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }
}