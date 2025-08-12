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
import androidx.compose.ui.res.painterResource
import com.example.vovotapesa.ui.UiState
import com.example.vovotapesa.ui.app.navigation.Rooter
import com.example.vovotapesa.viewmodel.AuthViewModel
import com.example.vovotapesa.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import com.example.vovotapesa.R
import com.example.vovotapesa.data.remote.dto.ProfileResponse

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
    is UiState.Loading -> CircularProgressIndicator()
    is UiState.Success -> {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {

        // Header

        Text(
            text =  "Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally))


        Spacer(modifier = Modifier.height(12.dp))

        // Profile Picture + Info in Row
//        Row(
//          verticalAlignment = Alignment.CenterVertically,
//          horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
          Box(
            modifier = Modifier
              .size(80.dp) // Smaller photo
              .clip(RoundedCornerShape(8.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
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
            Text("${profile?.user?.firstName}  ${profile?.user?.lastName}", fontWeight = FontWeight.Bold)
            Text(profile?.user?.email ?:"", fontSize = 12.sp)
            Text("Account Number: ${profile?.user?.account}", fontSize = 12.sp)
          }
//        }

        Spacer(modifier = Modifier.height(12.dp))

        // Action buttons
          Button(
            onClick = {
              scope.launch {
                authViewModel.logout()
                navController.navigate(Rooter.Login().name)
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
          ) {
            Icon(
              painter = painterResource(id = R.drawable.logout),
              contentDescription = "logout",
              modifier = Modifier.size(32.dp)
            )
          }


        Spacer(modifier = Modifier.height(16.dp))

        // Additional Info
        Text("Additional info", fontWeight = FontWeight.Bold, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(8.dp))

        ProfileInfoItemCompact("Phone number", profile?.user?.phone ?:"")
        ProfileInfoItemCompact("ID document", profile?.user?.idNumber ?:"" )
        ProfileInfoItemCompact("Current address", profile?.user?.fullAddress ?: "No have")
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

