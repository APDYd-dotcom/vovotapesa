package com.example.vovotapesa.ui.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vovotapesa.R
import com.example.vovotapesa.data.model.Country

@Composable
fun CountryDropdownWithFlags() {
  val context = LocalContext.current

  val countries = listOf(
    Country("Burundi", pref = "+257", R.drawable.bi),
    Country("Kenya", pref = "+260", R.drawable.ke),
    Country("Rwanda", pref = "+250",R.drawable.rw),
    Country("Tanzania", pref = "+255",R.drawable.tz),
    Country("Uganda", pref = "+257",R.drawable.ug),
//    Country("South Sudan", pref = "+257",R.drawable.flag_south_sudan),
    Country("RDC", pref = "+247",R.drawable.cd),
//    Country("Somalia", pref = "+257",R.drawable.Somalia)
  )


  var expanded by remember { mutableStateOf(false) }
  var selectedCountry by remember { mutableStateOf(countries[0]) }

  Box(modifier = Modifier.fillMaxWidth()
   // .padding(16.dp)
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
      onDismissRequest = { expanded = false },
      //modifier = Modifier.fillMaxWidth()
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
}