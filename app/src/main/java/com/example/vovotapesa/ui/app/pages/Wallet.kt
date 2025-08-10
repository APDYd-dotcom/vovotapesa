package com.example.vovotapesa.ui.app.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.vovotapesa.ui.app.components.MediumTextComponent
import com.example.vovotapesa.ui.app.components.NormalTextComponent
import com.example.vovotapesa.R

@Composable
fun WalletPage() {
  
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
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
      Row(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        MediumTextComponent("Balance\n$balance Units", color = MaterialTheme.colorScheme.onBackground)
        MediumTextComponent("Account number\n$agentCode", color = MaterialTheme.colorScheme.onBackground)
      }
    }

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row {
        Icon(
          painter = painterResource(id = R.drawable.arow_withdrow),
          contentDescription = "received",
          modifier = Modifier.size(25.dp)
        )
        Text("Received", color = Color(0xFF007BFF))
      }
     Row {
       Icon(
         painter = painterResource(id = R.drawable.arow_send),
         contentDescription = "received",
         modifier = Modifier.size(25.dp)
       )
       Text("Sent", color = Color(0xFF007BFF))
     }
      Row{
        Icon(
          painter = painterResource(id = R.drawable.search),
          contentDescription = "received",
          modifier = Modifier.size(25.dp)
        )
        Text("Search", color = Color(0xFF007BFF))
      }
    }

    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
              NormalTextComponent(txn.date, color= MaterialTheme.colorScheme.onBackground)
              NormalTextComponent(txn.name, color= MaterialTheme.colorScheme.onBackground)
              NormalTextComponent("${txn.amount} $", color= MaterialTheme.colorScheme.primary)
            }
          }
        }
      }
    }
  }
}

data class Transaction(val date: String, val name: String, val amount: Double)

@Preview(showBackground = true)
@Composable
fun Preview(){
  WalletPage()
}