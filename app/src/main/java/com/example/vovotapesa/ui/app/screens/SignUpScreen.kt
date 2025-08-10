package com.example.vovotapesa.ui.app.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.vovotapesa.R
import com.example.vovotapesa.data.model.Country
import com.example.vovotapesa.data.model.Document
import com.example.vovotapesa.data.remote.dto.AuthRegister
import com.example.vovotapesa.ui.UiState
import com.example.vovotapesa.ui.app.components.HeaderTextComponent
import com.example.vovotapesa.ui.app.components.MyPasswordTextField
import com.example.vovotapesa.ui.app.components.MyTextFieldComponent
import com.example.vovotapesa.ui.app.components.NormalTextComponent
import com.example.vovotapesa.viewmodel.AuthViewModel
import java.util.Calendar


@Composable
fun SignUpScreen(
  onLoginClick: ()-> Unit,
  authViewModel: AuthViewModel
) {
  var currentStep by remember { mutableStateOf(1) }
  val registerUiState by authViewModel.registerUiState.collectAsState()


    val firstname = authViewModel.firstname.collectAsState()
    val lastname = authViewModel.lastname.collectAsState()
    val birthDate = authViewModel.birthDate.collectAsState()
    val country = authViewModel.country.collectAsState()
    val document = authViewModel.document.collectAsState()
    val numero = authViewModel.numero.collectAsState()
    val phone = authViewModel.phone.collectAsState()
    val pin = authViewModel.pin.collectAsState()
    val email = authViewModel.email.collectAsState()
    val password = authViewModel.password.collectAsState()
    val confirmPassword = authViewModel.confirmPassword.collectAsState()

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 8.dp)
      .background(color = MaterialTheme.colorScheme.background)
  ) { innerppading ->
    Column(
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .padding(innerppading)
        .verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "logo",
        modifier = Modifier.size(size = 180.dp)
      )

      HeaderTextComponent(value = "Register an account")
      Spacer(modifier = Modifier.height(height = 8.dp))
      when (currentStep) {
        1 -> StepOne(
//          firstname = firstname.value.toString(),
//          lastname = lastname.value.toString(),
//          birthDate = birthDate.value.toString(),
          authViewModel = authViewModel
        )
        2 -> StepTwo(
          authViewModel = authViewModel,
        )
        3 -> StepThree(
          authViewModel = authViewModel,
        )
        4 -> StepFour(
          authViewModel = authViewModel
        )
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
            if (password.value != confirmPassword.value) {
              authViewModel.resetRegisterState(UiState.Error("Passwords do not match"))
            } else if (
              firstname.value?.isBlank() == true ||
              lastname.value?.isBlank() == true ||
              birthDate.value?.isBlank() == true ||
              country.value?.isBlank() == true ||
              document.value?.isBlank() == true ||
              numero.value?.isBlank() == true ||
              phone.value?.isBlank() == true ||
              pin.value?.isBlank() == true ||
              email.value?.isBlank() == true
            ) {
              authViewModel.resetRegisterState(UiState.Error("All fields are required"))
            } else {
              authViewModel.register(
                AuthRegister(
                  email = email.value.toString(),
                  birthDate = birthDate.value.toString(),
                  firstName = firstname.value.toString(),
                  lastName = lastname.value.toString(),
                  country = country.value.toString(),
                  typeOfDocument = document.value.toString(),
                  idNumber = numero.value.toString(),
                  phone = phone.value.toString(),
                  password = password.value.toString(),
                  pin = pin.value.toString()
                ),
                onSuccess = {
                  // Handle success here, e.g., show success message or navigate
                  println("Registration successful!")
                },
                onError = { errorMsg ->
                  // Handle error here, e.g., show error message
                  println("Registration failed: $errorMsg")
                }
              )

            }
          }) {
            Text("Submit")
          }
        }
      }

      Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
      ) {
        //ui state
        when (registerUiState) {
          is UiState.Loading -> CircularProgressIndicator()
          is UiState.Success -> {
            Text("Registration Successful", color = Color.Green)
          }
          is UiState.Error -> {
            SelectionContainer { Text((registerUiState as UiState.Error).sapor, color = Color.Red) }
          }
          else -> {}
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          NormalTextComponent(value = "Already have an account?",color = MaterialTheme.colorScheme.onBackground)
          TextButton(
            onClick = onLoginClick
          ) {
            NormalTextComponent(value = "Login now.", color = MaterialTheme.colorScheme.primary)
          }
        }
        Spacer(modifier = Modifier.height(height = 32.dp))
      }
    }
  }

}


// Step One
@Composable
fun StepOne(
  authViewModel: AuthViewModel,
) {
  val firstname = remember { mutableStateOf("") }
  val lastname = remember { mutableStateOf("") }
  val birthDate = remember { mutableStateOf("") }
  val context = LocalContext.current
  val calendar = Calendar.getInstance()

  val datePicker = remember {
    DatePickerDialog(
      context,
      { _: DatePicker, year: Int, month: Int, day: Int ->
        // Format to yyyy-MM-dd
        val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, day)
        birthDate.value = formattedDate
        authViewModel.setBd(formattedDate)
      },
      calendar.get(Calendar.YEAR),
      calendar.get(Calendar.MONTH),
      calendar.get(Calendar.DAY_OF_MONTH)
    )
  }

  MyTextFieldComponent(
    value = firstname.value,
    onValueChange = {
      firstname.value = it
      authViewModel.setFname(it)
    },
    labelText = "First Name",
    leadingIcon = Icons.Default.AccountCircle,
    modifier = Modifier.fillMaxWidth()
  )

  MyTextFieldComponent(
    value = lastname.value,
    onValueChange = {
      lastname.value = it
      authViewModel.setLname(it)
    },
    labelText = "Last Name",
    leadingIcon = Icons.Default.AccountCircle,
    modifier = Modifier.fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(8.dp))

  OutlinedTextField(
    value = birthDate.value,
    onValueChange = {},
    label = { Text(text = "Birth Date") },
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.DateRange,
        contentDescription = "Date",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clickable { datePicker.show() }
      )
    },
    modifier = Modifier
      .fillMaxWidth()
      .clickable { datePicker.show() },
    readOnly = true
  )
}



@Composable
fun StepTwo(

  authViewModel: AuthViewModel
) {
  val countries = listOf(
    Country("Burundi", pref = "+257", R.drawable.bi),
    Country("Kenya", pref = "+260", R.drawable.ke),
    Country("Rwanda", pref = "+250", R.drawable.rw),
    Country("Tanzania", pref = "+255", R.drawable.tz),
    Country("Uganda", pref = "+256", R.drawable.ug),
    Country("RDC", pref = "+243", R.drawable.cd),
  )

  val documents = listOf(
    Document("PASS", "Passport"),
    Document("CNI", "CNI"),
    Document("DL", "Driver Licence")
  )

  var expandedCountry by remember { mutableStateOf(false) }
  var selectedCountry by remember { mutableStateOf(countries[0]) }

  var expandedDoc by remember { mutableStateOf(false) }
  var selectedDocument by remember { mutableStateOf(documents[0]) }

  val phoneInput = remember { mutableStateOf("") }
  val numeroInput = remember { mutableStateOf("") }

  // Country Dropdown
  Box(modifier = Modifier.fillMaxWidth()) {
    OutlinedTextField(
      value = selectedCountry.name,
      onValueChange = {},
      readOnly = true,
      shape = RoundedCornerShape(10.dp),
      modifier = Modifier
        .fillMaxWidth()
        .clickable { expandedCountry = true },
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
            imageVector = if (expandedCountry) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier.clickable { expandedCountry = !expandedCountry }
          )
        }
      }
    )

    DropdownMenu(
      expanded = expandedCountry,
      onDismissRequest = { expandedCountry = false }
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
            authViewModel.setCountry(country.name) // ✅ correct
            // Also update full phone in ViewModel
            authViewModel.setPhone(country.pref + phoneInput.value)
            expandedCountry = false
          }
        )
      }
    }
  }

  Spacer(modifier = Modifier.height(8.dp))

  // Document Dropdown
  Box(modifier = Modifier.fillMaxWidth()) {
    OutlinedTextField(
      value = selectedDocument.label,
      onValueChange = {},
      readOnly = true,
      shape = RoundedCornerShape(10.dp),
      modifier = Modifier
        .fillMaxWidth()
        .clickable { expandedDoc = true },
      leadingIcon = {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(8.dp)
        ) {
          Text("Select")
          Spacer(modifier = Modifier.width(3.dp))
          Icon(
            imageVector = if (expandedDoc) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier.clickable { expandedDoc = !expandedDoc }
          )
        }
      }
    )

    DropdownMenu(
      expanded = expandedDoc,
      onDismissRequest = { expandedDoc = false }
    ) {
      documents.forEach { doc ->
        DropdownMenuItem(
          text = { Text(text = doc.label) },
          onClick = {
            selectedDocument = doc
            authViewModel.setDocument(doc.name) // ✅ send "PASS", not "Passport"
            expandedDoc = false
          }
        )
      }
    }
  }

  Spacer(modifier = Modifier.height(8.dp))

  // ID Number
  MyTextFieldComponent(
    value = numeroInput.value,
    onValueChange = {
      numeroInput.value = it
      authViewModel.setNum(it)
    },
    labelText = "ID Number",
    leadingIcon = Icons.Default.AccountBox,
    modifier = Modifier.fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(8.dp))

  // Phone Number (with country prefix shown)
  OutlinedTextField(
    value = phoneInput.value,
    onValueChange = {
      phoneInput.value = it
      authViewModel.setPhone(selectedCountry.pref + it) // ✅ include prefix
    },
    shape = RoundedCornerShape(10.dp),
    label = { Text("Phone Number") },
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
          imageVector = Icons.Default.Phone,
          contentDescription = null,
        )
      }
    },
    singleLine = true
  )
}


@Composable
fun StepThree(
  authViewModel: AuthViewModel
) {
  val pinState = remember { mutableStateOf("") }

  Spacer(modifier = Modifier.height(8.dp))

  MyTextFieldComponent(
    value = pinState.value,
    onValueChange = {
      pinState.value = it
      authViewModel.setPin(it) // ✅ Save to ViewModel
    },
    labelText = "PIN",
    leadingIcon = Icons.Default.Lock,
    keyboardType = KeyboardType.NumberPassword,
    modifier = Modifier.fillMaxWidth()
  )
}

@Composable
fun StepFour(
  authViewModel: AuthViewModel,
) {
  val emailState = remember { mutableStateOf("") }
  val passState = remember { mutableStateOf("") }
  val confirmPassState = remember { mutableStateOf("") }

  MyTextFieldComponent(
    value = emailState.value,
    onValueChange = {
      emailState.value = it
      authViewModel.setEmail(it)
    },
    labelText = "Email",
    leadingIcon = Icons.Default.Email,
    keyboardType = KeyboardType.Email,
    modifier = Modifier.fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(8.dp))

  MyPasswordTextField(
    value = passState.value,
    onValueChange = {
      passState.value = it
      authViewModel.setPass(it)
    },
    labelText = "Password",
    leadingIcon = Icons.Default.Lock,
    keyboardType = KeyboardType.Password,
    modifier = Modifier.fillMaxWidth()
  )

  Spacer(modifier = Modifier.height(4.dp))

  MyPasswordTextField(
    value = confirmPassState.value,
    onValueChange = {
      confirmPassState.value = it
      authViewModel.setConfPass(it)
    },
    labelText = "Confirm Password",
    leadingIcon = Icons.Default.Lock,
    keyboardType = KeyboardType.Password,
    modifier = Modifier.fillMaxWidth()
  )
}

