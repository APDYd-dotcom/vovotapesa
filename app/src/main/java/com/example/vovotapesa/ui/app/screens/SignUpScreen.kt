import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vovotapesa.R
import com.example.vovotapesa.data.model.Country
import com.example.vovotapesa.ui.app.components.MyTextFieldComponent
import java.util.*


@Composable
fun SignUpScreen() {
  var currentStep by remember { mutableStateOf(1) }

  // Form fields for each step
  var firstname by remember { mutableStateOf("") }
  var lastname by remember { mutableStateOf("") }
  var birthDate by remember { mutableStateOf("") }

  var country by remember { mutableStateOf("") }
  var numero by remember { mutableStateOf("") }

  var phone by remember { mutableStateOf("") }

  var pin by remember { mutableStateOf("") }

  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }

  Column(modifier = Modifier.padding(16.dp)) {
    when (currentStep) {
      1 -> StepOne(
        firstname = firstname,
        lastname = lastname,
        birthDate = birthDate,
        setFirstName = { firstname = it },
        onDateChange = { birthDate = it },
        setLastName = { lastname = it }
      )
      2 -> StepTwo( phone, setPhone = { phone = it }, numero, setNumero = { numero = it })
      3 -> StepThree( pin, onPinChange = { pin = it } )
      4 -> StepFour(email, password, onEmailChange = { email = it }, onPasswordChange = { password = it })
    }

    Spacer(modifier = Modifier.height(24.dp))

    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      if (currentStep > 1) {
        Button(onClick = { currentStep-- }) {
          Text("Previous")
        }
      }
      if (currentStep < 4) {
        Button(onClick = { currentStep++ }) {
          Text("Next")
        }
      } else {
        Button(onClick = {
          // Handle final form submission
          println("Submitting: $birthDate, $country, $numero, $phone, $pin, $email, $password")
        }) {
          Text("Submit")
        }
      }
    }
  }
}

// Step One
@Composable
fun StepOne(
  firstname: String,
  lastname: String,
  setLastName: (String) -> Unit,
  setFirstName: (String) -> Unit,
  birthDate: String,
  onDateChange: (String) -> Unit
) {
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

  MyTextFieldComponent(
    value = firstname,
    onValueChange = setFirstName,
    labelText = "First Name",
    leadingIcon = Icons.Default.AccountCircle,
    modifier = Modifier.fillMaxWidth()
  )

  MyTextFieldComponent(
    value = lastname,
    onValueChange = setLastName,
    labelText = "Laste Name",
    leadingIcon = Icons.Default.AccountCircle,
    modifier = Modifier.fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(8.dp))

  OutlinedTextField(
    value = birthDate,
    onValueChange = {},
    label = { Text(text = "Birth Date") },
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.DateRange,
        contentDescription = "Date",
        modifier = Modifier.clickable { datePicker.show() }
      )
    },
    modifier = Modifier
      .fillMaxWidth()
      .clickable { datePicker.show() },
    readOnly = true // Use false to make it read-only while still allowing the modifier
  )
}

// Step Two
@Composable
fun StepTwo(phone: String, setPhone: (String) -> Unit, numero: String, setNumero: (String) -> Unit) {

  val countries = listOf(
    Country("Burundi", pref = "+257", R.drawable.bi),
    Country("Kenya", pref = "+260", R.drawable.ke),
    Country("Rwanda", pref = "+250", R.drawable.rw),
    Country("Tanzania", pref = "+255", R.drawable.tz),
    Country("Uganda", pref = "+257", R.drawable.ug),
//    Country("South Sudan", pref = "+257",R.drawable.flag_south_sudan),
    Country("RDC", pref = "+247", R.drawable.cd),
//    Country("Somalia", pref = "+257",R.drawable.Somalia)
  )

  val documents = listOf("Passport", "CNI", "Driver Licence")


  var expanded by remember { mutableStateOf(false) }
  var selectedCountry by remember { mutableStateOf(countries[0]) }


  Box(
    modifier = Modifier.fillMaxWidth()
  ) {

    OutlinedTextField(
      value = selectedCountry.name,
      onValueChange = {},
      readOnly = true,
      shape = RoundedCornerShape(10.dp),
//      label = { Text("Select Country") },
      modifier = Modifier.fillMaxWidth()
        .clickable { expanded = true },
      leadingIcon = {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(8.dp)
        ) {
          Image(
            painter = painterResource(id = selectedCountry.flagResId),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            Modifier.clickable { expanded = !expanded }
          )
        }
      }
    )

    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false }
    ) {
      countries.forEach { country ->
        DropdownMenuItem(
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Image(
                painter = painterResource(id = country.flagResId),
                contentDescription = null,
                modifier = Modifier
                  .size(24.dp)
                  .padding(end = 8.dp)
              )
              Text(text = country.name)
            }
          },
          onClick = {
            selectedCountry = country
            expanded = false
          }
        )
      }
    }
  }

  Spacer(modifier = Modifier.height(8.dp))

  var expandedd by remember { mutableStateOf(false) }
  var selectedDocument by remember { mutableStateOf(documents[0]) }

  Box(
    modifier = Modifier.fillMaxWidth()
    // .padding(16.dp)
  ) {

    OutlinedTextField(
      value = selectedDocument,
      onValueChange = {},
      readOnly = true,
      shape = RoundedCornerShape(10.dp),
//      label = { Text("Select Country") },
      modifier = Modifier.fillMaxWidth()
        .clickable { expandedd = true },
      leadingIcon = {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(8.dp)
        ) {
          Text("Select")
          Spacer(modifier = Modifier.width(3.dp))
          Icon(
            imageVector = if (expandedd) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            Modifier.clickable { expandedd = !expanded }
          )
        }
      }
    )

    DropdownMenu(
      expanded = expandedd,
      onDismissRequest = { expandedd = false },
      //modifier = Modifier.fillMaxWidth()
    ) {
      documents.forEach { doc ->
        DropdownMenuItem(
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = doc)
            }
          },
          onClick = {
            selectedDocument = doc
            expandedd = false
          }
        )
      }
    }
  }

  MyTextFieldComponent(
    value = numero,
    onValueChange = setNumero,
    labelText = "ID Number",
    leadingIcon = Icons.Default.AccountBox,
    modifier = Modifier.fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(8.dp))
  OutlinedTextField(
    value = phone,
    onValueChange = setPhone,
    shape = RoundedCornerShape(10.dp),
//      label = { Text("Select Country") },
    modifier = Modifier.fillMaxWidth(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
    leadingIcon = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
      ) {
        Text(text = selectedCountry.pref)
        Spacer(modifier = Modifier.width(3.dp))
        Icon(
          imageVector = Icons.Default.Phone ,
          contentDescription = null,
        )
      }
    }
  )

}

// Step Three
@Composable
fun StepThree( pin: String, onPinChange: (String) -> Unit) {

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

// Step Four
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
