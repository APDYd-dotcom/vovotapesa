package com.example.vovotapesa.ui

data class UiState<T>(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val data: T? = null,
    val error: String? =  null
)