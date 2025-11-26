package com.example.vovotapesa.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vovotapesa.data.TokenManager
import com.example.vovotapesa.data.model.Country
import com.example.vovotapesa.data.remote.dto.AuthLogin
import com.example.vovotapesa.data.remote.dto.AuthRegister
import com.example.vovotapesa.data.remote.dto.AuthResponse
import com.example.vovotapesa.data.remote.dto.ProfileResponse
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

  private val _registerUiState = MutableStateFlow(UiState<Unit>())
  val registerUiState: StateFlow<UiState<Unit>> = _registerUiState

  private val _loginUiState = MutableStateFlow(UiState<AuthResponse>())
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

  private val _account = MutableStateFlow<String>("")
  val account = _account.asStateFlow()

  private val _currProfile = MutableStateFlow<ProfileResponse?>(null)
  val currProfile = _currProfile.asStateFlow()

  // ---------------- SETTERS -------------------
  fun setSelectedCountry(country: Country) { _selectedCountry.value = country }
  fun setFname(value: String) { _firstname.value = value }
  fun setLname(value: String) { _lastname.value = value }
  fun setBd(value: String) { _birthDate.value = value }
  fun setCountry(value: String) { _country.value = value }
  fun setDocument(value: String) { _document.value = value }
  fun setNum(value: String) { _numero.value = value }
  fun setPhone(value: String) { _phone.value = value }
  fun setPin(value: String) { _pin.value = value }
  fun setEmail(value: String) { _email.value = value }
  fun setPass(value: String) { _password.value = value }
  fun setConfPass(value: String) { _confirmPassword.value = value }
  fun setAccount(value: String) { _account.value = value }

  // ---------------- LOGIN -------------------
  fun login(request: AuthLogin) {
    viewModelScope.launch {
      _loginUiState.value = UiState(isLoading = true)

      val result = repository.login(request)
      result.fold(
        onSuccess = { resp ->
          tokenManager.saveAccessToken(resp.access)
          tokenManager.saveRefreshToken(resp.refresh)
          _loginUiState.value = UiState(success = true, data = resp)
        },
        onFailure = { e ->
          Log.e("AuthViewModel", "Login failed: ${e.message}", e)
          _loginUiState.value = UiState(error = e.message ?: "Check connection")
        }
      )
    }
  }

  // ---------------- REGISTER -------------------
  fun register(
    registerData: AuthRegister,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    viewModelScope.launch {
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

      _registerUiState.value = UiState(isLoading = true)

      val result = repository.register(registerData)
      result.fold(
        onSuccess = {
          _registerUiState.value = UiState(success = true, data = Unit)
          onSuccess()
        },
        onFailure = { e ->
          _registerUiState.value = UiState(error = e.message ?: "Unknown error")
          onError(e.message ?: "Unknown error")
        }
      )
    }
  }

  fun resetRegisterState(error: Error) { _registerUiState.value = UiState(error= error.message) }
  fun resetLoginState() { _loginUiState.value = UiState() }

  fun logout() {
    viewModelScope.launch { tokenManager.clear() }
  }

  val accessToken = tokenManager.accessToken.stateIn(
    viewModelScope,
    SharingStarted.Lazily,
    null
  )
}