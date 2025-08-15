package com.example.vovotapesa.ui.app.components.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileShimmer() {
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
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Title
    Text(
      text = "Profile",
      fontSize = 26.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Profile picture + info
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Spacer( // profile picture
        modifier = Modifier
          .size(90.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(shimmerBrush)
      )

      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Spacer( // name
          modifier = Modifier
            .width(140.dp)
            .height(18.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(shimmerBrush)
        )
        Spacer( // email
          modifier = Modifier
            .width(180.dp)
            .height(14.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(shimmerBrush)
        )
        Spacer( // account number
          modifier = Modifier
            .width(160.dp)
            .height(14.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(shimmerBrush)
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Shimmer for Logout Row
    Row(verticalAlignment = Alignment.CenterVertically) {
      // Icon placeholder
      Spacer(
        modifier = Modifier
          .size(20.dp)
          .clip(RoundedCornerShape(4.dp))
          .background(shimmerBrush)
      )

      Spacer(Modifier.width(8.dp))

      // Text placeholder
      Spacer(
        modifier = Modifier
          .width(60.dp) // approximate "Logout" width
          .height(16.dp)
          .clip(RoundedCornerShape(4.dp))
          .background(shimmerBrush)
      )
    }


    Spacer(modifier = Modifier.height(24.dp))

    // Additional info title
    Spacer(
      modifier = Modifier
        .fillMaxWidth(0.4f)
        .height(18.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(shimmerBrush)
    )

    Spacer(modifier = Modifier.height(12.dp))

    // Additional info items (3 rows)
    repeat(3) {
      Spacer(
        modifier = Modifier
          .fillMaxWidth()
          .height(40.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(shimmerBrush)
      )
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}
