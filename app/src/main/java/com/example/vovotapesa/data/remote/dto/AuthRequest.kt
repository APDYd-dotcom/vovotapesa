package com.example.vovotapesa.data.remote.dto

import android.accounts.Account
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRegister(
    val email: String,
    @SerialName("birth_date") val birthDate: String,
    @SerialName("first_name") val firstName : String,
    @SerialName("last_name") val lastName: String,
    val country: String,
    @SerialName("type_of_document") val typeOfDocument: String,
    @SerialName("id_number") val idNumber: String,
    val phone: String,
    val pin: String,
    val password: String,
    val account: String = ""
)

@Serializable
data class AuthLogin(
    val email: String,
    val password: String
)
