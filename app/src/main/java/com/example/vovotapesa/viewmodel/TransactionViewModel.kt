package com.example.vovotapesa.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vovotapesa.data.remote.dto.ConfirmTransactionRequest
import com.example.vovotapesa.data.remote.dto.TransactionResponse
import com.example.vovotapesa.data.remote.dto.VerifyTransactionRequest
import com.example.vovotapesa.data.repo.TransactionRepo
import com.example.vovotapesa.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor (private val repo: TransactionRepo): ViewModel() {
  private val _transactionUiState = MutableStateFlow(UiState<Any>())
  val transactionUiState: StateFlow<UiState<Any>> = _transactionUiState


  private val _transactions = MutableStateFlow<List<TransactionResponse>>(emptyList())
  val transactions = _transactions

  fun loadTransaction(token: String) {
    viewModelScope.launch {
      _transactionUiState.value = UiState(isLoading = true)
      val result = repo.getTransaction(token)
      Log.e("Transaction VM result:", "Detail: $result")

      result.fold(
        onSuccess = { list ->
          _transactions.value = list
          println("Transactions: $list")
          _transactionUiState.value = UiState(success = true )
        },
        onFailure = { e ->
          Log.e("Transaction VM error", "Failed to load transaction", e)
          _transactionUiState.value = UiState(error = e.message ?: "Unknown error")
        }
      )
    }
  }

  fun setIdle(){
    viewModelScope.launch {
      _transactionUiState.value = UiState()
    }
  }

  fun verifyTransaction(token: String, account: String, amount: String, onVerified: (String) -> Unit) {
    viewModelScope.launch {
      repo.verifyTransaction(token, VerifyTransactionRequest(account, amount)).fold(
        onSuccess = { response ->
          if (response.isValid) {
            onVerified(response.receiverName)
          } else {
            _transactionUiState.value = UiState(error = "Invalid account or insufficient balance")
          }
        },
        onFailure = { e ->
          _transactionUiState.value = UiState(error = e.message ?: "Verification failed")
        }
      )
    }
  }

  fun confirmTransaction(token: String, account: String, amount: String, pin: String) {
    viewModelScope.launch {
      repo.confirmTransaction(token, ConfirmTransactionRequest(account, amount, pin)).fold(
        onSuccess = { response ->
          when {
            response.fanta != null -> {
              _transactionUiState.value = UiState(success = true)
            }
          
          response.sapor != null -> {
          _transactionUiState.value = UiState(error = response.sapor)
        } else -> {
          _transactionUiState.value = UiState(error =  "Unknown response from server")
        } }},
        onFailure = { e ->
          _transactionUiState.value = UiState(error = e.message ?: "Confirmation failed")
        }

      )

    }
  }
}

