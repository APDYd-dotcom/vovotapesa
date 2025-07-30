package com.example.vovotapesa.ui.app.pages

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlertsPage(){
  val isLight = !isSystemInDarkTheme()

  val notifications = listOf(
    "Dear voovotapesa agent, you have got paid your 0.00 Units of commissions, your current main balance is 12.76",
    "Dear voovotapesa agent, you have got paid your 1.68 Units of commissions, your current main balance is 12.76",
    "180000 BIF have been deposit to ARAKAZA Prince Destin Yvan...",
    "Hello Agent Prince Destin Yvan ARAKAZA you have request the withdraw of 60 Units...",
    "Dear Voovotapesa Agent you have received 1.680 Units as commission...",
    "You have received 60 Units from Annie Graciella AKINDAVYI...",
    "You have receive 0.013727 Units from Illiminee...",
    "You have receive 0.006104 Units from APDY...",
    "You have receive 0.1 Units from APDY...",
    "You have receive 0.000001 Units from APDY..."
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
  ) {

    Text("Notifications", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))

    Spacer(modifier = Modifier.height(16.dp))

    LazyColumn(modifier = Modifier.fillMaxSize()) {
      items(notifications) { msg ->
        Card(
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = if (isLight) 0.6f else 1.0f))
        ) {
          Text(
            text = msg,
            modifier = Modifier.padding(12.dp),
            fontSize = 14.sp
          )
        }
      }
    }
  }
}
