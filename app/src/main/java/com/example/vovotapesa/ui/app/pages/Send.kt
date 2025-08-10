package com.example.vovotapesa.ui.app.pages

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.builtins.ArraySerializer

@Composable
fun SendPage() {
  var accountNumber by remember { mutableStateOf("") }
  var amount by remember { mutableStateOf("") }
  var showConfirmation by remember { mutableStateOf(false) }

  if (showConfirmation) {
    ConfirmationScreen(
      accountName = "HAKIZIMANA Pascal",
      amount = amount,
      exchangeRate = 7.0,
      onSend = {
        // Handle PIN send logic here
      },
      onBack = {
        showConfirmation = false
      }
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
            showConfirmation = true
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
  exchangeRate: Double,
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

    Text("You are about to withdraw $amount Units from $accountName.")
    Text("The exchange rate is $exchangeRate%.")

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
    ){

      TextButton(onClick = onBack) {
        Text("Back")
      }
      Spacer(modifier = Modifier.weight(weight = 0.3f))

      Button(onClick = { onSend(pin) }) {
        Text("Send")
      }
    }

  }
}
