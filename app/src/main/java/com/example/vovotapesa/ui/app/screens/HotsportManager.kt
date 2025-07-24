package com.example.vovotapesa.ui.app.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun LocalHotspotScreen() {
  val context = LocalContext.current
  var ssid by remember { mutableStateOf<String?>(null) }
  var password by remember { mutableStateOf<String?>(null) }
  var isHotspotRunning by remember { mutableStateOf(false) }

  Column(modifier = Modifier
    .fillMaxSize()
    .padding(16.dp)
  ) {
    Text("LocalOnlyHotspot Demo", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(20.dp))

    Button(onClick = {
      startLocalHotspot(context,
        onSuccess = { config ->
          ssid = config?.SSID
          password = config?.preSharedKey
          isHotspotRunning = true
        },
        onFailure = { error ->
          Toast.makeText(context, "Failed: $error", Toast.LENGTH_LONG).show()
        }
      )
    }) {
      Text("Start Hotspot")
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (isHotspotRunning) {
      Text("SSID: ${ssid ?: "Unknown"}")
      Text("Password: ${password ?: "Unknown"}")
    }
  }
}

fun startLocalHotspot(
  context: Context,
  onSuccess: (wifiConfiguration: android.net.wifi.WifiConfiguration?) -> Unit,
  onFailure: (reason: String) -> Unit
) {
  val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  // API 26+
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
      ContextCompat.checkSelfPermission(context, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {

      // Start hotspot
      //wifiManager.startLocalOnlyHotspot(hotspotCallback, handler)
      wifiManager.startLocalOnlyHotspot(object : WifiManager.LocalOnlyHotspotCallback() {
        override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation) {
          super.onStarted(reservation)
          val config = reservation.wifiConfiguration
          config?.let { onSuccess(it) } ?: onFailure("Config was null")
        }

        override fun onFailed(reason: Int) {
          super.onFailed(reason)
          Log.e("Hotspot", "Failed to start hotspot: $reason")
          onFailure("Error code: $reason")
        }
      }, Handler(Looper.getMainLooper()))

    } else {
      // Request permissions at runtime here
println("Permission denied")
    }
  } else {
    println("Android version must be at least API 26")
    // Show message: LocalOnlyHotspot not supported on this Android version
  }

}