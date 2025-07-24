package com.example.vovotapesa

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.example.vovotapesa.ui.app.screens.PermissionScreen


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      PermissionScreen()
    }
  }
}

// ViewModel-like state holder
object HotspotState {
  val ssid = MutableLiveData<String>()
  val password = MutableLiveData<String>()
  var reservation: WifiManager.LocalOnlyHotspotReservation? = null
}

@Composable
fun HotspotScreen() {
  val context = LocalContext.current
  val ssid by HotspotState.ssid.observeAsState()
  val password by HotspotState.password.observeAsState()

  val permissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission(),
    onResult = { granted ->
      if (granted) {
        startHotspot(context)
      }
    }
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    verticalArrangement = Arrangement.Center
  ) {
    Button(onClick = {
      val permission = Manifest.permission.ACCESS_FINE_LOCATION
      if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
        permissionLauncher.launch(permission)
      } else {
        startHotspot(context)
      }
    }) {
      Text("Start Local Hotspot")
    }

    Spacer(modifier = Modifier.height(24.dp))

    if (!ssid.isNullOrEmpty()) {
      Text("SSID: $ssid")
      Text("Password: $password")
    }
  }
}

fun startHotspot(context: Context) {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
    Log.e("Hotspot", "Not supported below Android 8.0")
    return
  }

  val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

  if (ActivityCompat.checkSelfPermission(
      context,
      Manifest.permission.CHANGE_WIFI_STATE
    ) != PackageManager.PERMISSION_GRANTED
  ) {
    Log.e("Hotspot", "Missing CHANGE_WIFI_STATE permission")
    return
  }

  wifiManager.startLocalOnlyHotspot(object : WifiManager.LocalOnlyHotspotCallback() {
    override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation) {
      super.onStarted(reservation)
      HotspotState.reservation = reservation
      val config = reservation.wifiConfiguration
      Log.d("Hotspot", "SSID: ${config?.SSID}, Password: ${config?.preSharedKey}")
      HotspotState.ssid.postValue(config?.SSID)
      HotspotState.password.postValue(config?.preSharedKey)
    }

    override fun onStopped() {
      super.onStopped()
      Log.d("Hotspot", "Hotspot stopped")
    }

    override fun onFailed(reason: Int) {
      super.onFailed(reason)
      Log.e("Hotspot", "Failed with reason: $reason")
    }
  }, Handler(Looper.getMainLooper()))
}