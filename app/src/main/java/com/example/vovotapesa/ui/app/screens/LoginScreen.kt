package com.example.vovotapesa.ui.app.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.vovotapesa.ui.app.components.NormalTextComponent

@Composable
fun LoginScreen(){
  Surface(modifier = Modifier.fillMaxSize()){
    NormalTextComponent(value = "Login")
  }
}