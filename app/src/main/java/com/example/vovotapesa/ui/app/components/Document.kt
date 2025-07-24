package com.example.vovotapesa.ui.app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DocumentsType(){
  val documents = listOf("Passport", "CNI", "Driver Licence")


  var expanded by remember { mutableStateOf(false) }
  var selectedCountry by remember { mutableStateOf(documents[0]) }

  Box(modifier = Modifier.fillMaxWidth()
    // .padding(16.dp)
  ) {

    OutlinedTextField(
      value = selectedCountry,
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
         Text("Select")
          Spacer(modifier = Modifier.width(3.dp))
          androidx.compose.material3.Icon(
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
      documents.forEach { doc ->
        DropdownMenuItem(
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = doc)
            }
          },
          onClick = {
            selectedCountry = doc
            expanded = false
          }
        )
      }
    }
  }

}
