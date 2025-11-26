package com.example.vovotapesa.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vovotapesa.ui.app.components.utils.launchWithState
import com.example.vovotapesa.data.TokenManager
import com.example.vovotapesa.data.model.PasswordChangeRequest
import com.example.vovotapesa.data.remote.dto.FantaResponse
import com.example.vovotapesa.data.remote.dto.ProfileResponse
import com.example.vovotapesa.data.repo.ProfileRepo
import com.example.vovotapesa.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileRepo,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(UiState<ProfileResponse>())
    val profileUiState = _profileUiState.asStateFlow()

    private val _profile = MutableStateFlow<ProfileResponse?>(null)
    val profile = _profile.asStateFlow()

    private val _editProfile = MutableStateFlow(false)
    val editProfile = _editProfile.asStateFlow()

    private val _editName = MutableStateFlow(false)
    val editName = _editName.asStateFlow()

    private val _editEmail = MutableStateFlow(false)
    val editEmail = _editEmail.asStateFlow()

    private val _editPassword = MutableStateFlow(false)
    val editPassword = _editPassword.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _photo = MutableStateFlow<File?>(null)
    val photo = _photo.asStateFlow()

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    // Profile update state (Result<Unit>)
    private val _profileState = MutableStateFlow(UiState<Unit>())
    val profileState = _profileState.asStateFlow()

    // Password update state (Result<Unit>)
    private val _pwdState = MutableStateFlow(UiState<FantaResponse>())
    val pwdState = _pwdState.asStateFlow()


    // ------------------------------------------------------------
    // UPDATE INPUT FIELDS
    // ------------------------------------------------------------
    fun updateEmail(email: String) = _email.tryEmit(email)
    fun updatePhoto(file: File) = _photo.tryEmit(file)
    fun updateName(name: String) = _fullName.tryEmit(name)
    fun updatePassword(password: String) = _password.tryEmit(password)


    // ------------------------------------------------------------
    // EDIT MODE
    // ------------------------------------------------------------
    fun toggleEditProfile() { _editProfile.value = !_editProfile.value }

    fun setIsEditName() { _editProfile.value = true; _editName.value = true }
    fun setIsEditEmail() { _editProfile.value = true; _editEmail.value = true }
    fun setIsEditPassword() { _editProfile.value = true; _editPassword.value = true }

    fun resetForm() {
        _email.value = ""
        _fullName.value = ""
        _password.value = ""
        _photo.value = null
    }

    fun resetEdit() {
        _editProfile.value = false
        _editPassword.value = false
        _editName.value = false
        _editEmail.value = false
    }


    // ------------------------------------------------------------
    // LOAD PROFILE
    // ------------------------------------------------------------
    fun loadProfile(token: String) {
        viewModelScope.launch {
            _profileUiState.value = UiState(isLoading = true)

            repo.getProfile(token).fold(
                onSuccess = {
                    _profile.value = it
                    _profileUiState.value = UiState(success = true, data = it)
                },
                onFailure = {
                    Log.e("ProfileVM", "Failed to load profile", it)
                    _profileUiState.value =
                        UiState(error = it.message ?: "Unknown error")
                }
            )
        }
    }


    // ------------------------------------------------------------
    // UPDATE PROFILE
    // ------------------------------------------------------------
    fun updateProfile(profile: ProfileResponse) {

        val email = _email.value.takeIf { it.isNotBlank() }
        val name = _fullName.value.takeIf { it.isNotBlank() }
        val photo = _photo.value

        if ((_editEmail.value && email == null) ||
            (_editName.value && name == null)
        ) {
            _profileState.value = UiState(error = "Veuillez remplir ce champ")
            return
        }

        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull()

            if (token == null) {
                _profileState.value = UiState(error = "No token, reconnect")
                return@launch
            }

            launchWithState(
                stateFlow = _profileState,
                block = {
                    repo.updateProfile(
                        id = profile.id,
                        token = token,
                        email = email,
                        fullName = name,
                        photo = photo
                    )
                },
                onSuccess = {
                    loadProfile(token)
                    resetForm()
                    resetEdit()
                }
            )
        }
    }


    // ------------------------------------------------------------
    // CHANGE PASSWORD
    // ------------------------------------------------------------
    fun changePassword() {
        if (_password.value.isBlank()) {
            _pwdState.value = UiState(error = "Le mot de passe ne doit pas être vide")
            return
        }

        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull()

            if (token == null) {
                _pwdState.value = UiState(error = "No token, reconnect")
                return@launch
            }

            launchWithState(
                stateFlow = _pwdState,
                block = {
                    repo.changePassword(
                        token = token,
                        request = PasswordChangeRequest(_password.value)
                    )
                },
                onSuccess = {
                    loadProfile(token)
                    resetForm()
                    resetEdit()
                }
            )
        }
    }
}