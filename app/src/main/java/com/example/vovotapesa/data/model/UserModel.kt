package com.example.vovotapesa.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Country(
  val name: String,
  val pref: String,
  val flagResId: Int
)

data class Identity(
  val type: String,
  val num: Int
)

data class Document(
  val label: String,
  val name: String
)
