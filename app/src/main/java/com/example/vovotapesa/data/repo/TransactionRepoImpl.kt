package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.ApiService
import com.example.vovotapesa.data.remote.dto.ConfirmTransactionRequest
import com.example.vovotapesa.data.remote.dto.TransactionResponse
import com.example.vovotapesa.data.remote.dto.VerifyTransactionRequest
import com.example.vovotapesa.data.remote.dto.VerifyTransactionResponse

class TransactionRepoImpl(private val api: ApiService) : TransactionRepo {
  override suspend fun getTransaction(token: String): Result<List<TransactionResponse>> {
    return try {
      val response: List<TransactionResponse> = api.getTransaction(token)
      Result.success(response)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun verifyTransaction(token: String, request: VerifyTransactionRequest): Result<VerifyTransactionResponse> {
    return try {
      val response = api.verifyTransaction(token, request)
      Result.success(response)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun confirmTransaction(token: String, request: ConfirmTransactionRequest): Result<TransactionResponse> {
    return try {
      val response = api.confirmTransaction(token, request)
      Result.success(response)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

}


