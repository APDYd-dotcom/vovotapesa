package com.example.vovotapesa.ui.app.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.vovotapesa.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
    shape = RoundedCornerShape(10.dp),
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
    shape = RoundedCornerShape(10.dp),
    singleLine = true
  )
}



@Composable
fun DateInputField(label: String = "Select Date") {
  val context = LocalContext.current
  val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

  val calendar = Calendar.getInstance()
  val year = calendar.get(Calendar.YEAR)
  val month = calendar.get(Calendar.MONTH)
  val day = calendar.get(Calendar.DAY_OF_MONTH)

  var selectedDate by remember { mutableStateOf("") }

  val datePickerDialog = DatePickerDialog(
    context,
    { _, y, m, d ->
      val pickedCalendar = Calendar.getInstance()
      pickedCalendar.set(y, m, d)
      selectedDate = dateFormat.format(pickedCalendar.time)
    },
    year,
    month,
    day
  )

  // Wrap in Box to make the whole field clickable
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    OutlinedTextField(
      value = selectedDate,
      onValueChange = {},
      label = { Text(label) },
      modifier = Modifier.fillMaxWidth(),
      readOnly = true,
      enabled = true, // disabled so it doesn't react to text input, only click
      leadingIcon = {
        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick date", modifier = Modifier.clickable { datePickerDialog.show() })
      }
    )
  }
}