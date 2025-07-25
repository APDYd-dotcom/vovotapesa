import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun SignUpScreen(){

  var step by remember { mutableStateOf(1) }

  // Form state variables
  var name by remember { mutableStateOf("") }
  var birthDate by remember { mutableStateOf("") }
  var country by remember { mutableStateOf("") }
  var document by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var pin by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }

  Scaffold{innerppading ->


    Column(modifier = Modifier.padding(16.dp).padding(innerppading)) {
      when (step) {
        1 -> StepOne(
          name,
          birthDate,
          onNameChange = { name = it },
          onDateChange = { birthDate = it })

        2 -> StepTwo(
          country,
          document,
          onCountryChange = { country = it },
          onDocumentChange = { document = it })

        3 -> StepThree(phone, pin, onPhoneChange = { phone = it }, onPinChange = { pin = it })
        4 -> StepFour(
          email,
          password,
          onEmailChange = { email = it },
          onPasswordChange = { password = it })
      }

      Spacer(modifier = Modifier.height(16.dp))

      Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        if (step > 1) {
          Button(onClick = { step-- }) {
            Text("Previous")
          }
        }
        if (step < 4) {
          Button(onClick = { step++ }) {
            Text("Next")
          }
        } else {
          Button(onClick = {
            // Submit form here
            println("Form submitted: $name, $birthDate, $country, $document, $phone, $pin, $email, $password")
          }) {
            Text("Submit")
          }
        }
      }
    }
  }
}

@Composable
fun StepOne(name: String, birthDate: String, onNameChange: (String) -> Unit, onDateChange: (String) -> Unit) {
  val context = LocalContext.current
  val calendar = Calendar.getInstance()

  val datePicker = remember {
    DatePickerDialog(
      context,
      { _: DatePicker, year: Int, month: Int, day: Int ->
        onDateChange("$day/${month + 1}/$year")
      },
      calendar.get(Calendar.YEAR),
      calendar.get(Calendar.MONTH),
      calendar.get(Calendar.DAY_OF_MONTH)
    )
  }

  OutlinedTextField(
    value = name,
    onValueChange = onNameChange,
    label = { Text("Name") },
    modifier = Modifier.fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(8.dp))

  OutlinedTextField(
    value = birthDate,
    onValueChange = {},
    label = { Text("Birth Date") },
    modifier = Modifier
      .fillMaxWidth()
      .clickable { datePicker.show() },
    enabled = false
  )
}

@Composable
fun StepTwo(country: String, document: String, onCountryChange: (String) -> Unit, onDocumentChange: (String) -> Unit) {
  OutlinedTextField(
    value = country,
    onValueChange = onCountryChange,
    label = { Text("Country") },
    modifier = Modifier.fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(8.dp))

  OutlinedTextField(
    value = document,
    onValueChange = onDocumentChange,
    label = { Text("ID Document") },
    modifier = Modifier.fillMaxWidth()
  )
}

@Composable
fun StepThree(phone: String, pin: String, onPhoneChange: (String) -> Unit, onPinChange: (String) -> Unit) {
  OutlinedTextField(
    value = phone,
    onValueChange = onPhoneChange,
    label = { Text("Phone Number") },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Phone
    ),
    modifier = Modifier.fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(8.dp))

  OutlinedTextField(
    value = pin,
    onValueChange = onPinChange,
    label = { Text("PIN") },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.NumberPassword
    ),
    modifier = Modifier.fillMaxWidth()
  )
}

@Composable
fun StepFour(email: String, password: String, onEmailChange: (String) -> Unit, onPasswordChange: (String) -> Unit) {
  OutlinedTextField(
    value = email,
    onValueChange = onEmailChange,
    label = { Text("Email") },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Email
    ),
    modifier = Modifier.fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(8.dp))

  OutlinedTextField(
    value = password,
    onValueChange = onPasswordChange,
    label = { Text("Password") },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Password
    ),
    modifier = Modifier.fillMaxWidth()
  )
}