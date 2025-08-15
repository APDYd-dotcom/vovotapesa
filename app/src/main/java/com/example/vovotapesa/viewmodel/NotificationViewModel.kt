package com.example.vovotapesa.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vovotapesa.data.remote.dto.NotificationResponse
import com.example.vovotapesa.data.repo.NotificationRepo
import com.example.vovotapesa.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
  private val repo: NotificationRepo
): ViewModel() {

  private val _notificationUiState = MutableStateFlow<UiState<List<NotificationResponse>>>(UiState.Idle)
  val notificationUiState: StateFlow<UiState<List<NotificationResponse>>> = _notificationUiState

  private val _notification = MutableStateFlow<List<NotificationResponse>>(emptyList())
  val notification: StateFlow<List<NotificationResponse>> = _notification

  fun loadNotification(token: String) {
    viewModelScope.launch {
      _notificationUiState.value = UiState.Loading

      val result = repo.getNotification(token)
      Log.e("Notification VM result", "Detail: $result")

      result.fold(
        onSuccess = { n ->
          _notification.value = n
          _notificationUiState.value = UiState.Success(n)
          println("Notification: $n")
        },
        onFailure = { e ->
          Log.e("Notification VM error", "Failed to load Notification", e)
          _notificationUiState.value = UiState.Error(e.message ?: "Unknown error")
        }
      )
    }
  }
}
