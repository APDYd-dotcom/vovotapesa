package com.example.vovotapesa.ui.app.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vovotapesa.ui.UiState
import com.example.vovotapesa.viewmodel.AuthViewModel
import com.example.vovotapesa.viewmodel.TransactionViewModel

@Composable
fun SendPage(
  transactionViewModel: TransactionViewModel,
  navController: NavController,
  authViewModel: AuthViewModel
) {
  var accountNumber by remember { mutableStateOf("") }
  var amount by remember { mutableStateOf("") }
  var receiverName by remember { mutableStateOf("") }
  var showConfirmation by remember { mutableStateOf(false) }

  val token by authViewModel.accessToken.collectAsState()
  val uiState by transactionViewModel.transactionUiState.collectAsState()

  // Show loading
  if (uiState is UiState.Loading) {
    Text("Loading...", modifier = Modifier.padding(16.dp))
  }

  // Show error
  if (uiState is UiState.Error) {
    Text(
      text = (uiState as UiState.Error).sapor,
      color = Color.Red,
      modifier = Modifier.padding(16.dp)
    )
    Log.e("Transaction error", "Detail: ${(uiState as UiState.Error).sapor}")
  }

  if (showConfirmation) {
    ConfirmationScreen(
      accountName = receiverName,
      amount = amount,
      onSend = { pin ->
        transactionViewModel.confirmTransaction(token.toString(), accountNumber, amount, pin)
      },
      onBack = { showConfirmation = false },
      navController = navController
    )
  } else {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(24.dp))
      Text("Send money", fontSize = 22.sp, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(24.dp))

      OutlinedTextField(
        value = accountNumber,
        onValueChange = { accountNumber = it },
        label = { Text("Account number") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      )

      OutlinedTextField(
        value = amount,
        onValueChange = { amount = it },
        label = { Text("Amount") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      )

      Button(
        onClick = {
          if (accountNumber.isNotBlank() && amount.isNotBlank()) {
            transactionViewModel.verifyTransaction(token.toString(), accountNumber, amount) { name ->
              receiverName = name
              showConfirmation = true
            }
          }
        },
        modifier = Modifier
          .align(Alignment.Start)
          .padding(top = 12.dp)
      ) {
        Text("Next")
      }
    }
  }
}

@Composable
fun ConfirmationScreen(
  accountName: String,
  amount: String,
  onSend: (String) -> Unit,
  onBack: () -> Unit,
  navController: NavController
) {
  var pin by remember { mutableStateOf("") }
  var showSuccessDialog by remember { mutableStateOf(false) }

  // ✅ Success Modal
  if (showSuccessDialog) {
    AlertDialog(
      onDismissRequest = { showSuccessDialog = false },
      title = { Text("Transaction Sent") },
      text = { Text("Your transaction request has been sent!") },
      confirmButton = {
        Button(onClick = {
          showSuccessDialog = false
          navController.navigate("wallet") {
            popUpTo("send") { inclusive = true } // Clear send page from backstack
          }
        }) {
          Text("OK")
        }
      }
    )
  } else {
    // ✅ Only show confirmation screen when modal is NOT open
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text("Confirm transaction", fontSize = 20.sp, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(16.dp))

      Text("You are about to send $amount $ to $accountName.")
      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(
        value = pin,
        onValueChange = { pin = it },
        label = { Text("Your code PIN") },
        modifier = Modifier.fillMaxWidth()
      )
      Spacer(modifier = Modifier.height(16.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        TextButton(onClick = onBack) {
          Text("Back")
        }
        Spacer(modifier = Modifier.weight(0.3f))
        Button(onClick = {
          onSend(pin)                // ✅ Call API
          showSuccessDialog = true   // ✅ Show modal
        }) {
          Text("Send")
        }
      }
    }
  }
}
