package com.example.vovotapesa.ui.app.pages.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vovotapesa.R
import com.example.vovotapesa.ui.app.components.ButtonCompose
import com.example.vovotapesa.ui.app.components.PasswordField
import com.example.vovotapesa.ui.app.components.utils.InputValidator
import com.example.vovotapesa.viewmodel.AuthViewModel
import com.example.vovotapesa.viewmodel.ProfileViewModel

@Composable
fun ProfileForm(
  authViewModel: AuthViewModel,
  profileViewModel: ProfileViewModel,
) {
  val fullName by profileViewModel.fullName.collectAsState()
  val email by profileViewModel.email.collectAsState()
  val newPassword by profileViewModel.password.collectAsState()

  val currProfile by authViewModel.currProfile.collectAsState()
  val profileState by profileViewModel.profileUiState.collectAsState()

  val editName by profileViewModel.editName.collectAsState()
  val editEmail by profileViewModel.editEmail.collectAsState()
  val editPassword by profileViewModel.editPassword.collectAsState()

  when {
    editName -> {
      TextField(
        value = fullName,
        onValueChange = { value -> profileViewModel.updateName(value) },
        label = { androidx.compose.material3.Text(text = stringResource(R.string.full_name)) },
        leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
      )
    }

    editEmail -> {
      TextField(
        value = email,
        onValueChange = { value -> profileViewModel.updateEmail(value) },
        label = { androidx.compose.material3.Text(text = stringResource(R.string.email)) },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
      )
    }

    editPassword -> {
      Spacer(modifier = Modifier.height(12.dp))
      PasswordField(
        value = newPassword,
        onValueChange = { value -> profileViewModel.updatePassword(value) },
        label = stringResource(R.string.new_pwd),
        leading = Icons.Default.Lock
      )
    }
  }

  Spacer(modifier = Modifier.height(24.dp))

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.End
  ) {
    ButtonCompose(
      text = stringResource(R.string.cancel),
      color = MaterialTheme.colorScheme.errorContainer,
      textColor = MaterialTheme.colorScheme.error
    ) {
      profileViewModel.resetEdit()
      profileViewModel.resetForm()
    }

    Spacer(modifier = Modifier.width(12.dp))

    ButtonCompose(
      text = stringResource(R.string.save),
      enabled = when {
        editName -> fullName.isNotBlank()
        editEmail -> email.isNotBlank() && InputValidator.isValidEmail(email)
        editPassword -> newPassword.isNotBlank()
        else -> true
      },
      isLoading = profileState.isLoading
    ) {
      currProfile?.let { profile ->
        when {
          editPassword -> profileViewModel.changePassword()
          editName || editEmail -> profileViewModel.updateProfile(profile)
        }
      }
    }
  }
}