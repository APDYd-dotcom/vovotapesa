package com.example.vovotapesa.data.repo

import com.example.vovotapesa.data.remote.dto.WalletResponse

interface WalletRepo {
  suspend fun getWallet(token: String): Result<WalletResponse>
}