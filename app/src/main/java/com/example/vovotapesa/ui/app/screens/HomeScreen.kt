package com.example.vovotapesa.ui.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.vovotapesa.ui.app.components.NormalTextComponent
import com.example.vovotapesa.ui.app.navigation.MyPageNavigation

@Composable
fun HomeScreen(){
  val navController = rememberNavController()
  Scaffold() {innerppading ->
    Column(
      modifier = Modifier.fillMaxSize()
        .padding(innerppading)
    ) {
      MyPageNavigation(navController)
    }
  }
}