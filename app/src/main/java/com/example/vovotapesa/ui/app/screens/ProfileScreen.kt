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
  //val tokenManager: TokenManager
  val scope = rememberCoroutineScope()
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp),

    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "My Profile",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.primary
    )

    Spacer(modifier = Modifier.height(24.dp))

    Box(
      modifier = Modifier
        .size(130.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
      Icon(
        imageVector = Icons.Default.Person,
        contentDescription = "Profile image",
        modifier = Modifier
          .align(Alignment.Center)
          .size(70.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Icon(
        imageVector = Icons.Default.Check,
        contentDescription = "Verified",
        tint = primaryBlue,
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(6.dp)
          .size(20.dp)
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      ProfileInfoItem(label = "Name", value = "Prince Destin Yvan ARAKAZA")
      ProfileInfoItem(label = "Email", value = "arakazaprincedestinyvan@gmail.com")
      ProfileInfoItem(label = "Agent code", value = "10004")
      ProfileInfoItem(label = "Commission", value = "0.00 Units")
    }

    Spacer(modifier = Modifier.height(16.dp))



    val scope = rememberCoroutineScope()

    Button(
      onClick = {
        scope.launch {
          authViewModel.logout()
          navController.navigate(Rooter.Login().name)
        }
      }
    ) {
      Text("Logout")
    }

/*
    Button(
       // onClick = onLogoutClick,
        onClick = {
          scope.launch {
            authViewModel.logout()
            navController.navigate(Rooter.Login().name)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
        shape = RoundedCornerShape(8.dp)
      ) {
        Text("Logout", color = Color.White)
      }
*/

    Spacer(modifier = Modifier.height(24.dp))

    Text("Additional info", fontWeight = FontWeight.Bold, fontSize = 18.sp)

    Spacer(modifier = Modifier.height(12.dp))

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      ProfileInfoItem(label = "Phone number", value = "67926885")
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text("ID document: P00056651", fontSize = 14.sp)
        Icon(Icons.Default.Check, contentDescription = null, tint = primaryBlue, modifier = Modifier.padding(start = 8.dp))
        Icon(Icons.Default.AccountBox, contentDescription = null, tint = primaryBlue, modifier = Modifier.padding(start = 4.dp))
      }
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Current address: Bujumbura-Tenga-NTumangende", fontSize = 14.sp)
        Icon(Icons.Default.Edit, contentDescription = null, tint = primaryBlue, modifier = Modifier.padding(start = 8.dp))
      }
    }

    Spacer(modifier = Modifier.height(16.dp))
//Button(
//  onClick = {}
//) {
//
//  Text(
//    text = "Change pin",
//    color = MaterialTheme.colorScheme.background,
////    textDecoration = TextDecoration.Underline,
////    modifier = Modifier.padding(top = 12.dp)
//  )
//}
  }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
  Column {
    Text(
      text = "$label:",
      fontSize = 14.sp,
      color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
      fontWeight = FontWeight.SemiBold
    )
    Text(
      text = value,
      fontSize = 14.sp,
      color = MaterialTheme.colorScheme.onBackground
    )
  }
}