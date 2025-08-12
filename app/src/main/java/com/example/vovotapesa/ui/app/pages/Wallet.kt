package com.example.vovotapesa.ui.app.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vovotapesa.ui.app.components.MediumTextComponent
import com.example.vovotapesa.ui.app.components.NormalTextComponent
import com.example.vovotapesa.R
import androidx.compose.runtime.getValue
import com.example.vovotapesa.ui.UiState
import com.example.vovotapesa.viewmodel.AuthViewModel
import com.example.vovotapesa.viewmodel.WalletViewModel

@Composable
fun WalletPage(
  walletViewModel: WalletViewModel,
  authViewModel: AuthViewModel
) {

  val token by authViewModel.accessToken.collectAsState()
  val walletUiState by walletViewModel.walletUiState.collectAsState()


  LaunchedEffect(token) {
    token?.let {
      walletViewModel.loadWallet(it)
      walletViewModel.loadWallet(token.toString())
    }
  }

  when (walletUiState) {
    is UiState.Loading -> CircularProgressIndicator()
    is UiState.Success -> { WalletUi(walletViewModel) }
    is UiState.Error -> Text("Check Connection ${walletUiState.toString()} ")
    else -> {}
  }


}

@Composable
fun WalletUi(walletViewModel: WalletViewModel) {

  val wallet = walletViewModel.wallet.collectAsState()

  val balance = wallet.value?.balance ?:"0.00"
  val account_number = wallet.value?.user?.account ?:"0.00"

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
        MediumTextComponent(
          "Balance\n$balance $",
          color = MaterialTheme.colorScheme.onBackground
        )
        MediumTextComponent(
          "Account number\n$account_number",
          color = MaterialTheme.colorScheme.onBackground
        )
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
      Row {
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
              NormalTextComponent(txn.date, color = MaterialTheme.colorScheme.onBackground)
              NormalTextComponent(txn.name, color = MaterialTheme.colorScheme.onBackground)
              NormalTextComponent("${txn.amount} $", color = MaterialTheme.colorScheme.primary)
            }
          }
        }
      }
    }
  }
}
data class Transaction(val date: String, val name: String, val amount: Double)
