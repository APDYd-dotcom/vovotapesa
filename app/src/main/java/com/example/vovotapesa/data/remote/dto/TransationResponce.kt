package com.example.vovotapesa.data.remote.dto

import com.example.vovotapesa.data.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
  val id: Int,
  val sender: User,
  val receiver: User,
  @SerialName("sender_name") val senderName: String,
  @SerialName("receiver_name") val receiverName: String,
  val amount: String,
  val reference: String,
  @SerialName("sent_at") val sentAt : String
)