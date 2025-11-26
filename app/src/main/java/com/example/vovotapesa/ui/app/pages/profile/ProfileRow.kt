package com.example.vovotapesa.ui.app.pages.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun ProfileRow(label: String, value: String, icon: Int? = null, onClick: () -> Unit? = {}) {
  Row(
    modifier = Modifier.fillMaxWidth().clickable { onClick() },
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    verticalAlignment = Alignment.Top
  ) {
    icon?.let { Icon(
       painterResource(it),
      contentDescription = label,
      modifier = Modifier.size(24.dp),
      tint = MaterialTheme.colorScheme.primary
    )}
    Spacer(Modifier.width(4.dp))
    Column(
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.primary
      )
      Text(
        text = value,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground
      )
    }
  }
}


@Composable
fun ProfileRowDown(label: String, icon: Int? = null, rotate: Float = 0f, onClick: () -> Unit? = {}) {
  Row(
    modifier = Modifier.fillMaxWidth().clickable { onClick() },
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    icon?.let { Icon(
      painterResource(it),
      contentDescription = label,
      modifier = Modifier.size(24.dp).rotate(rotate),
      tint = MaterialTheme.colorScheme.primary,
    )}
    Spacer(Modifier.width(4.dp))
    Column(
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.primary
      )
    }
  }
}