package com.example.vovotapesa.data.remote.dto

import com.example.vovotapesa.data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
  val id: Int,
  val user: User,
  val msg: String,
  val date: String
)
