package com.example.vovotapesa.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object FontSizes {
  @Composable
  fun title(): TextUnit {
    val width = LocalConfiguration.current.screenWidthDp
    return when {
      width < 360 -> 30.sp
      width < 600 -> 32.sp
      else -> 24.sp
    }
  }

  @Composable
  fun body(): TextUnit {
    val width = LocalConfiguration.current.screenWidthDp
    return when {
      width < 360 -> 14.sp
      width < 600 -> 16.sp
      else -> 18.sp
    }
  }

  @Composable
  fun caption(): TextUnit {
    val width = LocalConfiguration.current.screenWidthDp
    return when {
      width < 360 -> 12.sp
      width < 600 -> 13.5.sp
      else -> 14.sp
    }
  }
}

object Spacings {
  @Composable
  fun small() = (LocalConfiguration.current.screenWidthDp / 100).dp
  @Composable
  fun medium() = (LocalConfiguration.current.screenWidthDp / 50).dp
  @Composable
  fun large() = (LocalConfiguration.current.screenWidthDp / 25).dp
}