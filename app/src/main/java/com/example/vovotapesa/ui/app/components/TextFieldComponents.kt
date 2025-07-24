package com.example.vovotapesa.ui.app.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.vovotapesa.R

/*A Simple OutlinedTextField*/
@Composable
fun MyTextFieldComponent(
  modifier: Modifier = Modifier,
  value: String,
  onValueChange: (String) -> Unit,
  labelText: String,
  leadingIcon: ImageVector? = null,
  keyboardType: KeyboardType = KeyboardType.Text,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  enabled: Boolean = true
){
  OutlinedTextField(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    label = { Text(text = labelText) },
    leadingIcon = {
      if (leadingIcon != null) {
        Icon(imageVector = leadingIcon, contentDescription = null)
      }
    },
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    visualTransformation = visualTransformation,
    shape = RoundedCornerShape(percent = 30),
    singleLine = true,
    enabled = enabled
  )
}

/*A Password OutlinedTextField*/
@Composable
fun MyPasswordTextField(
  modifier: Modifier = Modifier,
  value: String,
  onValueChange: (String) -> Unit,
  labelText: String,
  leadingIcon: ImageVector? = null,
  keyboardType: KeyboardType = KeyboardType.Password,
  enabled: Boolean = true
) {
  var passwordVisible by rememberSaveable { mutableStateOf(false) }

  OutlinedTextField(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    label = { Text(text = labelText) },
    leadingIcon = {
      if (leadingIcon != null) {
        Icon(imageVector = leadingIcon, contentDescription = null)
      }
    },
    trailingIcon = {
      val iconResource = if (passwordVisible) {
        painterResource(id = R.drawable.visible)
      } else {
        painterResource(id = R.drawable.visibiltyof)
      }
      val description = if (passwordVisible) "Hide password" else "Show password"

      IconButton(onClick = { passwordVisible = !passwordVisible }) {
        Icon(painter = iconResource, contentDescription = description)
      }
    },
    enabled = enabled,
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
    shape = RoundedCornerShape(percent = 30),
    singleLine = true
  )
}