package com.example.vovotapesa.ui.app.screens

import android.R.attr.password
import android.widget.Button
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vovotapesa.ui.app.components.HeaderTextComponent
import com.example.vovotapesa.R
import com.example.vovotapesa.ui.app.components.CountryDropdownWithFlags
import com.example.vovotapesa.ui.app.components.DocumentsType
import com.example.vovotapesa.ui.app.components.MediumTextComponent
import com.example.vovotapesa.ui.app.components.MyPasswordTextField
import com.example.vovotapesa.ui.app.components.MyTextFieldComponent
import com.example.vovotapesa.ui.app.components.NormalTextComponent

@Composable
fun SignUpScreen(){
  val ( email, setEmail) = rememberSaveable { mutableStateOf("") }
  val ( password, setPassword) = rememberSaveable { mutableStateOf("") }

  Scaffold { innerppading ->
    Column(
      modifier = Modifier.fillMaxSize()
        .padding(innerppading)
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo",
        modifier = Modifier.size(size = 180.dp)
      )

      HeaderTextComponent(value = "Login")
      Spacer(modifier = Modifier.height(height = 8.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ){
        CountryDropdownWithFlags()
//        Spacer(modifier = Modifier.width(3.dp))
//        MyTextFieldComponent(
//          value = email,
//          onValueChange = setEmail,
//          labelText = "Email",
//          leadingIcon = Icons.Default.Email,
//        )
      }

      Spacer(modifier = Modifier.height(height = 8.dp))
      DocumentsType()
      Spacer(modifier = Modifier.height(height = 4.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        TextButton(
          onClick = {}
        ) {
          MediumTextComponent(value = "Forget password?", color = MaterialTheme.colorScheme.primary)
        }
      }
      Spacer(modifier = Modifier.height(height = 4.dp))

      Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          NormalTextComponent(value = "Not yet registered?",color = Color.Black)
          TextButton(
            onClick = {}
          ) {
            NormalTextComponent(value = "Create an account now.", color = MaterialTheme.colorScheme.primary)
          }
        }
        Spacer(modifier = Modifier.height(height = 32.dp))
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun preview(){
  SignUpScreen()
}