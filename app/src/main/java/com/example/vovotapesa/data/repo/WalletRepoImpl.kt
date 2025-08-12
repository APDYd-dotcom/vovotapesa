package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.ApiService
import com.example.vovotapesa.data.remote.dto.WalletResponse

class WalletRepoImpl( private val api: ApiService): WalletRepo {
  override suspend fun getWallet(token: String): Result<WalletResponse> {
    return try {
      val response = api.getWallet(token)
      Result.success(response[0])
    }catch (e: Exception){
      Result.failure(e)
    }
  }
}