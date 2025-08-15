package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.ApiService
import com.example.vovotapesa.data.remote.dto.TransactionResponse

class TransactionRepoImpl(private val api: ApiService): TransactionRepo {
  override suspend fun getTransaction(token: String): Result<List<TransactionResponse>> {
    return try {
      val response: List<TransactionResponse> = api.getTransaction(token)
      Result.success(response)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}

