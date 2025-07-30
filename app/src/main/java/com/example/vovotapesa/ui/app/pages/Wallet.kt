package com.example.vovotapesa.ui.app.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WalletPage() {

  val isLight = !isSystemInDarkTheme()
  val balance = 12.76
  val agentCode = "10004"
  val transactions = remember {
    listOf(Transaction("2024-12-27", "Nikezwe Marie Prisca", 1.0))
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colorScheme.background)
  ) {
//    Header()

    Text(
      text = "My wallet",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 12.dp)
    )

    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = if (isLight) 0.6f else 1.0f))
    ) {
      Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text("Balance\n$balance Units", fontSize = 16.sp)
        Text("Agent code: $agentCode", fontSize = 16.sp)
      }
    }

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text("\uD83D\uDC47Received", color = Color(0xFF007BFF))
      Text("\uD83D\uDD17Sent", color = Color(0xFF007BFF))
      Text("\uD83D\uDD0DSearch", color = Color(0xFF007BFF))
    }

    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = if (isLight) 0.6f else 1.0f))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text("Transactions", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("Date", fontWeight = FontWeight.Bold)
          Text("Name", fontWeight = FontWeight.Bold)
          Text("Amount", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))

        LazyColumn {
          items(transactions) { txn ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(txn.date)
              Text(txn.name)
              Text("${txn.amount} Units", color = Color(0xFF007BFF))
            }
          }
        }
      }
    }
  }
}

//@Composable
//fun Header() {
//  Row(
//    modifier = Modifier
//      .fillMaxWidth()
//      .padding(16.dp),
//    horizontalArrangement = Arrangement.SpaceBetween,
//    verticalAlignment = Alignment.CenterVertically
//  ) {
//    Text(
//      text = "Vtpesa",
//      color = Color(0xFF008FFF),
//      fontSize = 26.sp,
//      fontWeight = FontWeight.Bold
//    )

//    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
//      Icon(Icons.Default.Home, contentDescription = "Home")
//      Icon(Icons.Default.Check, contentDescription = "Withdraw")
//      Icon(Icons.Default.Send, contentDescription = "Send")
//      Icon(Icons.Default.AccountBox, contentDescription = "Wallet")
//      Icon(Icons.Default.Notifications, contentDescription = "Alerts")
//    }

//    Box(
//      modifier = Modifier
//        .size(40.dp)
//        .background(Color.Gray, shape = CircleShape)
//    )
//  }
//}

data class Transaction(val date: String, val name: String, val amount: Double)

@Preview(showBackground = true)
@Composable
fun preview(){
  WalletPage()
}