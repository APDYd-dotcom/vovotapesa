package com.example.vovotapesa.ui.app.components.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WalletShimmer() {
  val transition = rememberInfiniteTransition(label = "")
  val shimmerTranslate by transition.animateFloat(
    initialValue = 0f,
    targetValue = 1000f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = ""
  )

  val shimmerBrush = Brush.linearGradient(
    colors = listOf(
      Color.LightGray.copy(alpha = 0.6f),
      Color.LightGray.copy(alpha = 0.3f),
      Color.LightGray.copy(alpha = 0.6f)
    ),
    start = Offset(0f, 0f),
    end = Offset(shimmerTranslate, shimmerTranslate)
  )

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
// Balance Card shimmer
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
      Row(
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        // Left column: Balance
        Column {
          Spacer( // "Balance" label
            modifier = Modifier
              .width(80.dp)
              .height(16.dp)
              .clip(RoundedCornerShape(4.dp))
              .background(shimmerBrush)
          )
          Spacer(modifier = Modifier.height(8.dp))
          Spacer( // Balance value
            modifier = Modifier
              .width(100.dp)
              .height(20.dp)
              .clip(RoundedCornerShape(4.dp))
              .background(shimmerBrush)
          )
        }

        // Right column: Account number
        Column(horizontalAlignment = Alignment.End) {
          Spacer( // "Account number" label
            modifier = Modifier
              .width(120.dp)
              .height(16.dp)
              .clip(RoundedCornerShape(4.dp))
              .background(shimmerBrush)
          )
          Spacer(modifier = Modifier.height(8.dp))
          Spacer( // Account number value
            modifier = Modifier
              .width(100.dp)
              .height(20.dp)
              .clip(RoundedCornerShape(4.dp))
              .background(shimmerBrush)
          )
        }
      }
    }


    // Row with actions shimmer
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
        .padding(horizontal = 20.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      repeat(4) {
        Spacer(
          modifier = Modifier
            .width(60.dp)
            .height(24.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(shimmerBrush)
        )
      }
    }

    // Transactions Card shimmer
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Spacer(
          modifier = Modifier
            .fillMaxWidth(0.4f)
            .height(20.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(shimmerBrush)
        )

        Spacer(Modifier.height(8.dp))

        // Table headers shimmer
        Row(
          modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 18.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          repeat(3) {
            Spacer(
              modifier = Modifier
                .fillMaxWidth(0.25f)
                .height(20.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(shimmerBrush)
            )
          }
        }

        Spacer(Modifier.height(8.dp))

        // Transaction rows shimmer
        LazyColumn {
          items(11) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Date column (narrow)
              Spacer(
                modifier = Modifier
                  .fillMaxWidth(0.2f) // matches date text width
                  .height(20.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(shimmerBrush)
              )

              // Name column (wider)
              Spacer(
                modifier = Modifier
                  .fillMaxWidth(0.6f) // matches name text width
                  .height(20.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(shimmerBrush)
              )

              // Amount column (narrow)
              Spacer(
                modifier = Modifier
                  .fillMaxWidth(0.4f) // matches amount text width
                  .height(20.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(shimmerBrush)
              )
            }
          }
        }

      }
    }
  }
}
