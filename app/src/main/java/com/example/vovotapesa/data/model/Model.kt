package com.example.vovotapesa.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Country(
  val name: String,
  val pref: String,
  val flagResId: Int
)

data class Document(
  val label: String,
  val name: String
)

@Serializable
data class User(
  val email: String,
  @SerialName("birth_date") val birthDate: String? = null,
  @SerialName("first_name") val firstName : String,
  @SerialName("last_name") val lastName: String,
  val country: String,
  @SerialName("type_of_document") val typeOfDocument: String,
  @SerialName("id_number") val idNumber: String,
  val phone: String,
  val pin: String,
  val password: String = "",
  val account: String = "",
  @SerialName("profile_picture") val profilePicture: String = "",
  @SerialName("identity_picture") val identityPicture: String? = "",
  @SerialName("full_address") val fullAddress : String? = "",
  val verifier: Boolean = false
)


