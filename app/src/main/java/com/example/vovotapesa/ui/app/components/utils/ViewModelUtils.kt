package com.example.vovotapesa.ui.app.components.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vovotapesa.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun <T> ViewModel.launchWithState(
  stateFlow: MutableStateFlow<UiState<T>>,
  block: suspend () -> Result<T>,
  onSuccess: (T) -> Unit = {},
  onFailure: (Throwable) -> Unit = {},
) {
  // Set loading state
  stateFlow.value = UiState(isLoading = true)

  viewModelScope.launch {
    block().fold(
      onSuccess = { data ->
        stateFlow.value = UiState(
          isLoading = false,
          success = true,
          data = data
        )
        onSuccess(data)
      },
      onFailure = { e ->
        stateFlow.value = UiState(
          isLoading = false,
          error = e.message
        )
        onFailure(e)
      }
    )
  }
}


