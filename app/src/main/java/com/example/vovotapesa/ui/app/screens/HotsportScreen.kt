package com.example.vovotapesa.ui.app.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.core.app.ActivityCompat

@Composable
fun FineLocationScreen() {
  val context = LocalContext.current
  val permissionState = remember { mutableStateOf(false) }

  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission(),
    onResult = { isGranted ->
      permissionState.value = isGranted
    }
  )

  Column(
    modifier = Modifier.fillMaxSize().padding(24.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (permissionState.value) {
      LocalHotspotScreen()
      Text("✅ Location permission granted")
    } else {
      Text("📍 Location permission is required to start hotspot")
      Spacer(modifier = Modifier.height(16.dp))
      Button(onClick = {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
      }) {
        Text("Grant Location Permission")
      }
    }
  }
}
