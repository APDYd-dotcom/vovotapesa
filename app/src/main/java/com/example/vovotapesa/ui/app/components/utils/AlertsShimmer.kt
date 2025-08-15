package com.example.vovotapesa.ui.app.components.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlertShimmer() {
  // Animation
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
      .padding(horizontal = 16.dp)
  ) {
    // Title placeholder (same position as "Notifications")
    Text(
      text = "Notifications",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.align(Alignment.CenterHorizontally))

    Spacer(modifier = Modifier.height(16.dp))

    // Cards placeholders
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      items(11) { // 6 fake notifications
        Card(
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Spacer(
            modifier = Modifier
              .fillMaxWidth()
              .height(40.dp) // matches approx. your text height + padding
              .clip(RoundedCornerShape(6.dp))
              .background(shimmerBrush)
              .padding(12.dp)
          )
        }
      }
    }
  }
}



