package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.dto.ConfirmTransactionRequest
import com.example.vovotapesa.data.remote.dto.TransactionResponse
import com.example.vovotapesa.data.remote.dto.VerifyTransactionRequest
import com.example.vovotapesa.data.remote.dto.VerifyTransactionResponse

interface TransactionRepo {
  suspend fun getTransaction(token: String): Result<List<TransactionResponse>>
  suspend fun verifyTransaction(token: String, request: VerifyTransactionRequest): Result<VerifyTransactionResponse>
  suspend fun confirmTransaction(token: String, request: ConfirmTransactionRequest): Result<TransactionResponse>
}


