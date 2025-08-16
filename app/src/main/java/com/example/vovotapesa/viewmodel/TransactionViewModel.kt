package com.example.vovotapesa.viewmodel

import android.util.Log
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
  private val _transactionUiState = MutableStateFlow<UiState<List<TransactionResponse>>>(UiState.Idle)
  val transactionUiState: StateFlow<UiState<List<TransactionResponse>>> = _transactionUiState

  private val _transactions = MutableStateFlow<List<TransactionResponse>>(emptyList())
  val transactions = _transactions

  fun loadTransaction(token: String) {
    viewModelScope.launch {
      _transactionUiState.value = UiState.Loading
      val result = repo.getTransaction(token)
      Log.e("Transaction VM result:", "Detail: $result")

      result.fold(
        onSuccess = { list ->
          _transactions.value = list
          println("Transactions: $list")
          _transactionUiState.value = UiState.Success(list)
        },
        onFailure = { e ->
          Log.e("Transaction VM error", "Failed to load transaction", e)
          _transactionUiState.value = UiState.Error(e.message ?: "Unknown error")
        }
      )
    }
  }

  fun verifyTransaction(token: String, account: String, amount: String, onVerified: (String) -> Unit) {
    viewModelScope.launch {
      repo.verifyTransaction(token, VerifyTransactionRequest(account, amount)).fold(
        onSuccess = { response ->
          if (response.isValid) {
            onVerified(response.receiverName)
          } else {
            _transactionUiState.value = UiState.Error("Invalid account or insufficient balance")
          }
        },
        onFailure = { e ->
          _transactionUiState.value = UiState.Error(e.message ?: "Verification failed")
        }
      )
    }
  }

  fun confirmTransaction(token: String, account: String, amount: String, pin: String) {
    viewModelScope.launch {
      repo.confirmTransaction(token, ConfirmTransactionRequest(account, amount, pin)).fold(
        onSuccess = { transaction ->
          _transactions.value = _transactions.value + transaction
          _transactionUiState.value = UiState.Success(listOf(transaction))
        },
        onFailure = { e ->
          _transactionUiState.value = UiState.Error(e.message ?: "Confirmation failed")
        }
      )
    }
  }

}