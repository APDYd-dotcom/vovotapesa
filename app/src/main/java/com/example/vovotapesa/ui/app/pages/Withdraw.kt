package com.example.vovotapesa.ui.app.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WithdrawPage(){
  var accountNumber by remember { mutableStateOf("") }
  var amount by remember { mutableStateOf("") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
//    Header()

    Spacer(modifier = Modifier.height(24.dp))

    Text("Withdraw money", fontSize = 22.sp, fontWeight = FontWeight.Bold)

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
      onClick = { /* Navigate to confirmation */ },
      modifier = Modifier.align(Alignment.Start).padding(top = 12.dp)
    ) {
      Text("Next")
    }
  }




}