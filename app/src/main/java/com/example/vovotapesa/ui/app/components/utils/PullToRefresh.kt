package com.example.vovotapesa.ui.app.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PullToRefresh(
  isRefreshing: Boolean,
  onRefresh: suspend () -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  //val swiperState = rememberSwipeRefreshState(isRefreshing)
}