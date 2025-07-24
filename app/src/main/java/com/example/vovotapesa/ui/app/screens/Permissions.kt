package com.example.vovotapesa.ui.app.screens

import android.Manifest
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen() {
  val context = LocalContext.current

  val permissionState = rememberPermissionState(
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
      Manifest.permission.NEARBY_WIFI_DEVICES
    else
      Manifest.permission.ACCESS_FINE_LOCATION
  )

  var hotspotInfo by remember { mutableStateOf("Not started yet") }

  Column(modifier = Modifier.padding(16.dp)) {
    Button(
      onClick = { permissionState.launchPermissionRequest() }
    ) {
      Text("Request Nearby Devices Permission")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(onClick = {
      if (permissionState.status.isGranted) {
        startLocalOnlyHotspot(context) { ssid, password ->
          hotspotInfo = "SSID: $ssid\nPassword: $password"
        }
      } else {
        hotspotInfo = "Permission not granted"
      }
    }) {
      Text("Start LocalOnlyHotspot")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(hotspotInfo)
  }
}

@RequiresApi(Build.VERSION_CODES.O)
fun startLocalOnlyHotspot(
  context: Context,
  onSuccess: (String, String?) -> Unit
) {
  val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

  if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CHANGE_WIFI_STATE)
    != android.content.pm.PackageManager.PERMISSION_GRANTED
  ) {
    Log.e("Hotspot", "Missing CHANGE_WIFI_STATE permission")
    return
  }

  wifiManager.startLocalOnlyHotspot(object : WifiManager.LocalOnlyHotspotCallback() {
    override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation) {
      super.onStarted(reservation)
      val config = reservation.wifiConfiguration
      Log.d("Hotspot", "Started: ${config?.SSID} / ${config?.preSharedKey}")
      onSuccess(config?.SSID ?: "Unknown SSID", config?.preSharedKey)
    }

    override fun onStopped() {
      Log.d("Hotspot", "Stopped")
    }

    override fun onFailed(reason: Int) {
      Log.e("Hotspot", "Failed: $reason")
    }
  }, Handler(Looper.getMainLooper()))
}