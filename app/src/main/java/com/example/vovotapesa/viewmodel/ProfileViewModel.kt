package com.example.vovotapesa.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vovotapesa.data.remote.dto.ProfileResponse
import com.example.vovotapesa.data.repo.ProfileRepo
import com.example.vovotapesa.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repo: ProfileRepo): ViewModel() {
    private val _profileUiState = MutableStateFlow<UiState<ProfileResponse>>(UiState.Idle)
    val profileUiState: StateFlow<UiState<ProfileResponse>> = _profileUiState


    val _profile = MutableStateFlow<ProfileResponse?>(null)
    val profile = _profile

    fun loadProfile(token: String) {
        viewModelScope.launch {
            _profileUiState.value = UiState.Loading
            val result = repo.getProfile(token)
            Log.e("Profile VM result","Detail: $result")
            result.fold(
                onSuccess = { p ->
                    _profile.value = p
                    println("Profile: $p")
                    _profileUiState.value = UiState.Success(p)
                },
                onFailure = { e ->
                    Log.e("ProfileVM error", "Failed to load profile", e)
                    _profileUiState.value = UiState.Error(e.message ?: "Unknown error")
                }
            )
        }
    }
}