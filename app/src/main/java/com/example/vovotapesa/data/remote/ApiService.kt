package com.example.vovotapesa.data.remote

import com.example.vovotapesa.data.model.PasswordChangeRequest
import com.example.vovotapesa.data.remote.dto.AuthLogin
import com.example.vovotapesa.data.remote.dto.AuthRegister
import com.example.vovotapesa.data.remote.dto.AuthResponse
import com.example.vovotapesa.data.remote.dto.ConfirmTransactionRequest
import com.example.vovotapesa.data.remote.dto.ConfirmTransactionResponse
import com.example.vovotapesa.data.remote.dto.FantaResponse
import com.example.vovotapesa.data.remote.dto.NotificationResponse
import com.example.vovotapesa.data.remote.dto.ProfileResponse
import com.example.vovotapesa.data.remote.dto.TransactionResponse
import com.example.vovotapesa.data.remote.dto.VerifyTransactionRequest
import com.example.vovotapesa.data.remote.dto.VerifyTransactionResponse
import com.example.vovotapesa.data.remote.dto.WalletResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import java.io.File

class ApiService(private val client: HttpClient) {
    val baseUrl = "https://pesa.clubtechlac.bi/api"

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

    suspend fun getWallet(token: String): List<WalletResponse> {
        return client.get("$baseUrl/wallet/") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

    suspend fun getNotification(token: String): List<NotificationResponse> {
        return client.get("$baseUrl/notification/"){
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

    suspend fun getTransaction(token: String): List<TransactionResponse> {
        return client.get("$baseUrl/transaction/"){
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

     suspend fun verifyTransaction(token: String, request: VerifyTransactionRequest): VerifyTransactionResponse {
        return client.post("$baseUrl/send/") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun confirmTransaction(
        token: String,
        request: ConfirmTransactionRequest
    ): ConfirmTransactionResponse {
        return client.post("$baseUrl/confirm/") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun updateProfile(
        id: Int,
        token: String,
        email: String? = null,
        fullName: String? = null,
        photo: File? = null
    ): HttpResponse {
        return client.request("$baseUrl/profile/$id/") {
            method = HttpMethod.Patch
            header("Authorization", "Bearer $token")
            setBody(
                MultiPartFormDataContent(
                    formData {
                        email?.let { append("email", it) }
                        fullName?.let { append("user.full_name", it) }
                        photo?.let { file ->
                            append("photo", file.readBytes(), Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                            })
                        }
                    }
                )
            )
        }
    }

    suspend fun changePassword(token: String, request: PasswordChangeRequest): FantaResponse {
        val resp = client.post("$baseUrl/changepwd/") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(request)
        }
        return resp.body()
    }

}