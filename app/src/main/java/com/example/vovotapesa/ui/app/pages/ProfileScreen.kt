package com.example.vovotapesa.ui.app.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import androidx.compose.runtime.State
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import com.example.vovotapesa.ui.UiState
import com.example.vovotapesa.ui.app.navigation.Rooter
import com.example.vovotapesa.viewmodel.AuthViewModel
import com.example.vovotapesa.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import com.example.vovotapesa.R
import com.example.vovotapesa.data.remote.dto.ProfileResponse
import com.example.vovotapesa.ui.app.components.utils.ProfileShimmer

@Composable
fun ProfileScreen(
  onLogoutClick: () -> Unit,
  navController: NavHostController,
  authViewModel: AuthViewModel,
  profileViewModel: ProfileViewModel
) {
  val primaryBlue = Color(0xFF008CFF)
  val scope = rememberCoroutineScope()

  val profile by profileViewModel.profile.collectAsState()
  val profileUiState by profileViewModel.profileUiState.collectAsState()

  val token by authViewModel.accessToken.collectAsState()
  LaunchedEffect(token) {
    token?.let {
      profileViewModel.loadProfile(it)
      profileViewModel.loadProfile(token.toString())
    }
  }


  when (profileUiState) {
    is UiState.Loading -> { ProfileShimmer() }
    is UiState.Success -> {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Header
        Text(
          text = "Profile",
          fontSize = 26.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Profile Picture + Info in Row
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(16.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Box(
            modifier = Modifier
              .size(90.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .shadow(4.dp, RoundedCornerShape(12.dp))
          ) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = "Profile image",
              modifier = Modifier
                .align(Alignment.Center)
                .size(50.dp),
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
              "${profile?.user?.firstName ?: ""} ${profile?.user?.lastName ?: ""}",
              fontWeight = FontWeight.Bold,
              fontSize = 18.sp
            )
            Text(profile?.user?.email ?: "", fontSize = 13.sp, color = Color.Gray)
            Text("Account Number: ${profile?.user?.account ?: ""}", fontSize = 13.sp, color = Color.Gray)
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Button (Logout)
        Button(
          onClick = {
            scope.launch {
              authViewModel.logout()
              navController.navigate(Rooter.Login().name)
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth(0.5f)
        ) {
          Icon(
            painter = painterResource(id = R.drawable.logout),
            contentDescription = "logout",
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
          )
          Spacer(Modifier.width(8.dp))
          Text("Logout")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Additional Info Section
        Text(
          "Additional info",
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProfileInfoItemCompact("Phone number", profile?.user?.phone ?: "")
        ProfileInfoItemCompact("ID document", profile?.user?.idNumber ?: "")
        ProfileInfoItemCompact("Current address", profile?.user?.fullAddress ?: "No address")
      }

    }

    is UiState.Error -> Text("Check connexion")
    else -> {}
  }
}
  @Composable
  fun ProfileInfoItemCompact(label: String, value: String) {
    Column(
      modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 12.dp)
    ) {
      Text("$label: $value", fontSize = 12.sp)
    }
  }

