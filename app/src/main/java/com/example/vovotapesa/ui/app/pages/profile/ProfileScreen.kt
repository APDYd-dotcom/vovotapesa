package com.example.vovotapesa.ui.app.pages.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vovotapesa.R
import com.example.vovotapesa.ui.app.components.ImagePicker
import com.example.vovotapesa.viewmodel.AuthViewModel
import com.example.vovotapesa.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
  authViewModel: AuthViewModel,
  profileViewModel: ProfileViewModel
) {
  val profile by profileViewModel.profile.collectAsState()
  val profileState by profileViewModel.profileUiState.collectAsState()
  val selectedFile by profileViewModel.photo.collectAsState()
  val isEdit by profileViewModel.editProfile.collectAsState()

  LaunchedEffect(Unit) {
    if (profile == null) {
      authViewModel.accessToken.value?.let {
        profileViewModel.loadProfile(it)
      }
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp)
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(Modifier.height(24.dp))

    // -------------------- PROFILE PHOTO -----------------------
    Box(
      modifier = Modifier
        .size(100.dp)
        .clip(CircleShape),
      contentAlignment = Alignment.Center
    ) {
      if (profile?.user?.profilePicture != null) {
        AsyncImage(
          model = profile?.user?.profilePicture,
          contentDescription = "Profile photo",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .clip(CircleShape)
            .size(100.dp)
        )
      } else {
        Image(
          painter = painterResource(R.drawable.avatar),
          contentDescription = "Default photo",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .clip(CircleShape)
            .size(100.dp)
        )
      }
    }

    Spacer(Modifier.height(8.dp))

    // ---------------- IMAGE PICKER ----------------
    ImagePicker(
      isCenter = true,
      text = R.string.edit_photo,
      isAuto = true,
      loading = profileState.isLoading,
      success = profileState.success,
      error = profileState.error ?: "",
      selectedFile = selectedFile,
      onSubmit = {
        profile?.let { profileViewModel.updateProfile(it) }
      },
      onImageSelected = { file ->
        profileViewModel.updatePhoto(file)
      }
    )

    Spacer(Modifier.height(16.dp))

    // ---------------- EDIT MODE -----------------
    if (isEdit) {
      if (!profileState.error.isNullOrEmpty()) {
        Text(
          text = profileState.error ?: "An error occurred",
          color = MaterialTheme.colorScheme.error
        )
      }

      ProfileForm(authViewModel, profileViewModel)
    } else {

      // ---------------- PROFILE INFO (READ MODE) ----------------
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        ProfileRow(
          label = "Name",
          value = "${profile?.user?.firstName ?: ""} ${profile?.user?.lastName ?: ""}",
          icon = R.drawable.user
        ) { profileViewModel.setIsEditName() }

        ProfileRow(
          label = "Phone",
          value = "+${profile?.user?.phone ?: ""}",
          icon = R.drawable.phone
        )

        ProfileRow(
          label = "Email",
          value = profile?.user?.email ?: "",
          icon = R.drawable.email
        ) { profileViewModel.setIsEditEmail() }

        ProfileRow(
          label = "Account number",
          value = profile?.user?.account ?: "",
          icon = R.drawable.today
        )

        ProfileRowDown(
          label = "Change password",
          icon = R.drawable.lock
        ) { profileViewModel.setIsEditPassword() }

        ProfileRowDown(
          label = "Logout",
          icon = R.drawable.logout,
          rotate = 180f
        ) { authViewModel.logout() }

        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("Version 1.0.0", style = MaterialTheme.typography.bodySmall)
        }
      }
    }
  }
}