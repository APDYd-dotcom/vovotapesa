package com.example.vovotapesa.ui.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun App(){
  val navController = rememberNavController()
  Surface(
    modifier = Modifier.fillMaxWidth()
      .background(color = MaterialTheme.colorScheme.background)
  ) {
      MyNavigation(navController)
  }
}