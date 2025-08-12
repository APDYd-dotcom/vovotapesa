package com.example.vovotapesa.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vovotapesa.data.remote.dto.WalletResponse
import com.example.vovotapesa.data.repo.WalletRepo
import com.example.vovotapesa.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WalletViewModel @Inject constructor ( private val repo: WalletRepo): ViewModel() {
  private val _walletUiState = MutableStateFlow<UiState<WalletResponse>>(UiState.Idle)
  val walletUiState: StateFlow<UiState<WalletResponse>> = _walletUiState

  private val _wallet = MutableStateFlow<WalletResponse?>(null)
  val wallet = _wallet


  fun loadWallet(token: String) {
    viewModelScope.launch {
      _walletUiState.value = UiState.Loading
      val result = repo.getWallet(token)
      Log.e("Wallet VM result", "Detail: $result")

      result.fold(
        onSuccess = { w ->
          _wallet.value = w
          println("Wallet: $w")
          _walletUiState.value = UiState.Success(w)
        },
        onFailure = { e ->
         // _error.value = e.message.toString()
          Log.e("Wallet VM error", "Failed to load Wallet", e)
          _walletUiState.value = UiState.Error(e.message ?: "Unknown error")
        }
      )
    }
  }
}