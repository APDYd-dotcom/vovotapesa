package com.example.vovotapesa.ui.app.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vovotapesa.ui.UiState
import com.example.vovotapesa.viewmodel.AuthViewModel
import com.example.vovotapesa.viewmodel.TransactionViewModel

@Composable
fun SendPage(
  transactionViewModel: TransactionViewModel,
  authViewModel: AuthViewModel
) {
  var accountNumber by remember { mutableStateOf("") }
  var amount by remember { mutableStateOf("") }
  var receiverName by remember { mutableStateOf("") }
  var showConfirmation by remember { mutableStateOf(false) }

  val token by authViewModel.accessToken.collectAsState()
  val uiState by transactionViewModel.transactionUiState.collectAsState()

  // Optional: show loading indicator or error message
  if (uiState is UiState.Loading) {
    // show a simple loading text or spinner
    Text("Loading...", modifier = Modifier.padding(16.dp))
  }

  if (uiState is UiState.Error) {
    Text(
      text = (uiState as UiState.Error).sapor,
      color = androidx.compose.ui.graphics.Color.Red,
      modifier = Modifier.padding(16.dp)
    )
    Log.e("Transaction error","Detail: ${(uiState as UiState.Error).sapor}")
  }

  if (showConfirmation) {
    ConfirmationScreen(
      accountName = receiverName,
      amount = amount,
      onSend = { pin ->
        transactionViewModel.confirmTransaction(token.toString(), accountNumber, amount, pin)
      },
      onBack = { showConfirmation = false }
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
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
      )

      OutlinedTextField(
        value = amount,
        onValueChange = { amount = it },
        label = { Text("Amount") },
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
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
        modifier = Modifier.align(Alignment.Start).padding(top = 12.dp)
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
  onBack: () -> Unit
) {
  var pin by remember { mutableStateOf("") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("Confirm transaction", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))

    Text("You are about to withdraw $amount $ from $accountName.")
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
      Button(onClick = { onSend(pin) }) {
        Text("Send")
      }
    }
  }
}
