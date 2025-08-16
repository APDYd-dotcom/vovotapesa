package com.example.vovotapesa.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyTransactionRequest(
  @SerialName("receiver_account") val receiverAccount: String,
  val amount: String
)

@Serializable
data class VerifyTransactionResponse(
  @SerialName("receiver_name") val receiverName: String,
  val isValid: Boolean
)

@Serializable
data class ConfirmTransactionRequest(
  @SerialName("receiver_account") val receiverAccount: String,
  val amount: String,
  val pin: String
)

