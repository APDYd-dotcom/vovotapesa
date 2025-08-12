package com.example.vovotapesa.data.remote.dto

import com.example.vovotapesa.data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class WalletResponse(
  val id: Int,
  val user: User,
  val balance: String
)
