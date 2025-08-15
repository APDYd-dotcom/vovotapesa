package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.dto.TransactionResponse

interface TransactionRepo {
  suspend fun getTransaction(token: String): Result<List<TransactionResponse>>
}