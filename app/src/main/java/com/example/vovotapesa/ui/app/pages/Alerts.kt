package com.example.vovotapesa.ui.app.pages

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import com.example.vovotapesa.ui.UiState
import com.example.vovotapesa.ui.app.components.utils.AlertShimmer
import com.example.vovotapesa.viewmodel.AuthViewModel
import com.example.vovotapesa.viewmodel.NotificationViewModel

@Composable
fun AlertsPage(
  notificationViewModel: NotificationViewModel,
  authViewModel: AuthViewModel
){
 val token by authViewModel.accessToken.collectAsState()
  val notificationUiSte by notificationViewModel.notificationUiState.collectAsState()

  LaunchedEffect(token) {
    token?.let {
      notificationViewModel.loadNotification(it)
      notificationViewModel.loadNotification(token.toString())
    }
  }
when (notificationUiSte) {
  is UiState.Loading -> { AlertShimmer() }
  is UiState.Success -> {AlertUi(notificationViewModel = notificationViewModel)}
  is UiState.Error -> { AlertShimmer() }
  else -> {}
  }
}

@Composable
fun AlertUi(notificationViewModel: NotificationViewModel){
  val notification = notificationViewModel.notification.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
  ) {

    Text(
      text = "Notifications",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.align(Alignment.CenterHorizontally))

    Spacer(modifier = Modifier.height(16.dp))

    val notificationList by notificationViewModel.notification.collectAsState()

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      items(notificationList) { msg ->
        Card(
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
          )
        ) {
          Text(
            text = msg.msg,
            modifier = Modifier.padding(12.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    }
  }
}


