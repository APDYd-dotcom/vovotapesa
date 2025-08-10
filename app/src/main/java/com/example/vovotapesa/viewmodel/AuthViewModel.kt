package com.example.vovotapesa.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vovotapesa.data.TokenManager
import com.example.vovotapesa.data.model.Country
import com.example.vovotapesa.data.remote.dto.AuthLogin
import com.example.vovotapesa.data.remote.dto.AuthRegister
import com.example.vovotapesa.data.remote.dto.AuthResponse
import com.example.vovotapesa.data.repo.AuthRepo
import com.example.vovotapesa.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val repository: AuthRepo,
  private val tokenManager: TokenManager
) : ViewModel() {

  private val _registerUiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
  val registerUiState: StateFlow<UiState<Unit>> = _registerUiState

  private val _loginUiState = MutableStateFlow<UiState<AuthResponse>>(UiState.Idle)
  val loginUiState: StateFlow<UiState<AuthResponse>> = _loginUiState

  private val _selectedCountry = MutableStateFlow<Country?>(null)
  val selectedCountry = _selectedCountry.asStateFlow()

  private val _firstname = MutableStateFlow<String?>(null)
  val firstname = _firstname.asStateFlow()

  private val _lastname = MutableStateFlow<String?>(null)
  val lastname = _lastname.asStateFlow()

  private val _birthDate = MutableStateFlow<String?>(null)
  val birthDate = _birthDate.asStateFlow()

  private val _country = MutableStateFlow<String?>(null)
  val country = _country.asStateFlow()

  private val _numero = MutableStateFlow<String?>(null)
  val numero = _numero.asStateFlow()

  private val _document = MutableStateFlow<String?>(null)
  val document = _document.asStateFlow()

  private val _phone = MutableStateFlow<String?>(null)
  val phone = _phone.asStateFlow()

  private val _pin = MutableStateFlow<String?>(null)
  val pin = _pin.asStateFlow()

  private val _email = MutableStateFlow<String?>(null)
  val email = _email.asStateFlow()

  private val _password = MutableStateFlow<String?>(null)
  val password = _password.asStateFlow()

  private val _confirmPassword = MutableStateFlow<String?>(null)
  val confirmPassword = _confirmPassword.asStateFlow()

  private val _account = MutableStateFlow<String>("") // ✅ Initialize to empty string
  val account = _account.asStateFlow()

  // setters
  fun setSelectedCountry(country: Country) = run { _selectedCountry.value = country }
  fun setFname(value: String) = run { _firstname.value = value }
  fun setLname(value: String) = run { _lastname.value = value }
  fun setBd(value: String) = run { _birthDate.value = value }
  fun setCountry(value: String) = run { _country.value = value }
  fun setDocument(value: String) = run { _document.value = value }
  fun setNum(value: String) = run { _numero.value = value }
  fun setPhone(value: String) = run { _phone.value = value }
  fun setPin(value: String) = run { _pin.value = value }
  fun setEmail(value: String) = run { _email.value = value }
  fun setPass(value: String) = run { _password.value = value }
  fun setConfPass(value: String) = run { _confirmPassword.value = value }
  fun setAccount(value: String) = run { _account.value = value }

  fun login(request: AuthLogin) {
    viewModelScope.launch {
      _loginUiState.value = UiState.Loading
      val result = repository.login(request)
      result.fold(
        onSuccess = { resp ->
          tokenManager.saveAccessToken(resp.access)
          tokenManager.saveRefreshToken(resp.refresh)
          _loginUiState.value = UiState.Success(resp)
        },
        onFailure = { e ->
          Log.e("AuthViewModel", "Login failed: ${e.message}", e)
          _loginUiState.value = UiState.Error(e.message ?: "Unknown error")
        }
      )
    }
  }

  fun register(
    registerData: AuthRegister,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    viewModelScope.launch {
      // Simple validation example
      if (
        registerData.email.isBlank() ||
        registerData.firstName.isBlank() ||
        registerData.lastName.isBlank() ||
        registerData.birthDate.isBlank() ||
        registerData.country.isBlank() ||
        registerData.typeOfDocument.isBlank() ||
        registerData.idNumber.isBlank() ||
        registerData.phone.isBlank() ||
        registerData.password.isBlank() ||
        registerData.pin.isBlank()
      ) {
        onError("Please fill in all required fields.")
        return@launch
      }

      _registerUiState.value = UiState.Loading

      val result = repository.register(registerData)
      result.fold(
        onSuccess = {
          _registerUiState.value = UiState.Success(Unit)
          onSuccess()
        },
        onFailure = { e ->
          _registerUiState.value = UiState.Error(e.message ?: "Unknown error")
          onError(e.message ?: "Unknown error")
        }
      )
    }
  }


  fun resetRegisterState(error: UiState.Error) {
    _registerUiState.value = UiState.Idle
  }

  fun resetLoginState() {
    _loginUiState.value = UiState.Idle
  }

  fun logout() {
    viewModelScope.launch {
      tokenManager.clear()
    }
  }

  val accessToken = tokenManager.accessToken.stateIn(
    viewModelScope,
    SharingStarted.Lazily,
    null
  )
}
