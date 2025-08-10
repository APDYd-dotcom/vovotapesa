package com.example.vovotapesa.ui.app.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vovotapesa.ui.app.navigation.Rooter
import com.example.vovotapesa.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
  onLogoutClick: () -> Unit,
  navController: NavHostController,
  authViewModel: AuthViewModel
) {
  val primaryBlue = Color(0xFF008CFF)
  val scope = rememberCoroutineScope()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(12.dp), // Reduced padding
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    // Header
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "Profile",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Profile Picture + Info in Row
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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
        Text("Prince Destin Yvan ARAKAZA", fontWeight = FontWeight.Bold)
        Text("arakazaprincedestinyvan@gmail.com", fontSize = 12.sp)
        Text("Agent code: 10004", fontSize = 12.sp)
        Text("Commission: 0.00 Units", fontSize = 12.sp)
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Action buttons
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      Button(
        onClick = { /* Cash logic */ },
        colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
      ) {
        Text("Cash", color = Color.White)
      }
      Button(
        onClick = {
          scope.launch {
            authViewModel.logout()
            navController.navigate(Rooter.Login().name)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
      ) {
        Text("Logout", color = Color.White)
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Additional Info
    Text("Additional info", fontWeight = FontWeight.Bold, fontSize = 14.sp)

    Spacer(modifier = Modifier.height(8.dp))

    ProfileInfoItemCompact("Phone number", "67926885")
    ProfileInfoItemCompact("ID document", "P00056651")
    ProfileInfoItemCompact("Current address", "Bujumbura-Tenga-NTumangende")
  }
}

@Composable
fun ProfileInfoItemCompact(label: String, value: String) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text("$label: $value", fontSize = 12.sp)
  }
}
