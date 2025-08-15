package com.example.vovotapesa.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vovotapesa.data.remote.dto.TransactionResponse
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
}