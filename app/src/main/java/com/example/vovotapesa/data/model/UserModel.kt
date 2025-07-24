package com.example.vovotapesa.data.model

data class User(
  val name: String
)

data class Country(
  val name: String,
  val flagResId: Int
)

data class Identity(
  val type: String,
  val num: Int
)

