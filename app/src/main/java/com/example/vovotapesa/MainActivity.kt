package com.example.vovotapesa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vovotapesa.ui.app.components.CountryDropdownWithFlags
import com.example.vovotapesa.ui.app.navigation.App
import com.example.vovotapesa.ui.theme.VovotapesaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      VovotapesaTheme{
        App()
      }
    }
  }
}
