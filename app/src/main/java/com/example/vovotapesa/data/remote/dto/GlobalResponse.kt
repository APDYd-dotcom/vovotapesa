package com.example.vovotapesa.data.remote.dto

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class GlobalResponse(
  val fanta: String? = null,
  val sapor: String? = null
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class FantaResponse(val fanta: String? = null)