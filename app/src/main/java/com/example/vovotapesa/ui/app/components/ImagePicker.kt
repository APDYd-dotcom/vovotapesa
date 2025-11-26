package com.example.vovotapesa.ui.app.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.vovotapesa.R
import kotlinx.coroutines.delay
import java.io.File
import kotlin.io.copyTo
import kotlin.io.outputStream
import kotlin.io.use
import kotlin.let
import kotlin.text.isNotBlank

@Composable
fun ImagePicker(
  allowCapture: Boolean = false,
  isCenter: Boolean = false,
  isAuto: Boolean = false,
  text: Int? = null,
  onSubmit: () -> Unit = {},
  success: Boolean = false,
  loading: Boolean = false,
  selectedFile: File? = null,
  error: String = "",
  onImageSelected: (File) -> Unit
) {
  val context = LocalContext.current
  var showMessage by remember { mutableStateOf(false) }
  var messageText by remember { mutableStateOf("") }
  var messageType by remember { mutableStateOf("info") }
  var tempFile by remember { mutableStateOf<File?>(null) }

  LaunchedEffect(showMessage) {
    if (showMessage) {
      delay(2000)
      showMessage = false
    }
  }

  // Handle status changes
  LaunchedEffect(loading, success, error) {
    when {
      loading -> {
        messageText = context.getString(R.string.loading)
        messageType = "info"
        showMessage = true
      }
      success -> {
        messageText = context.getString(R.string.upload_success)
        messageType = "success"
        showMessage = true
      }
      error.isNotBlank() -> {
        messageText = error
        messageType = "error"
        showMessage = true
      }
    }
  }

  val messageColor = when (messageType) {
    "success" -> MaterialTheme.colorScheme.tertiary
    "error" -> MaterialTheme.colorScheme.error
    else -> MaterialTheme.colorScheme.primary
  }

  val pickImageLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri ->
    uri?.let {
      val file = uriToFile(context, it)
      onImageSelected(file)
      if (isAuto) onSubmit()
    }
  }

  val takePictureLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.TakePicture()
  ) { success ->
    if (success && tempFile != null) {
      onImageSelected(tempFile!!)
      tempFile = null
    }
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 0.dp),
    horizontalAlignment = if (isCenter) Alignment.CenterHorizontally else Alignment.Start
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = if (isCenter) Arrangement.Center else Arrangement.SpaceBetween
    ) {
      Text(
        text = selectedFile?.name ?: stringResource(text ?: R.string.pick_img),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
          .padding(start = 4.dp)
          .clickable {
            pickImageLauncher.launch(
              PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
          },
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
      )

      if (!isCenter) {
        Row {
          IconButton(onClick = {
            pickImageLauncher.launch(
              PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
          }) {
            Icon(
              painter = painterResource(R.drawable.image),
              contentDescription = "Pick image",
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(28.dp)
            )
          }

          if (allowCapture) {
            IconButton(onClick = {
              val photoFile = File.createTempFile("IMG_", ".jpg", context.cacheDir)
              val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                photoFile
              )
              tempFile = photoFile
              takePictureLauncher.launch(uri)
            }) {
              Icon(
                painter = painterResource(R.drawable.camera),
                contentDescription = "Capture image",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
              )
            }
          }
        }
      }
    }
    AnimatedVisibility(visible = showMessage, enter = fadeIn(), exit = fadeOut()) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .padding(top = 4.dp)
      ) {
        if (loading) {
          CircularProgressIndicator(
            modifier = Modifier
              .size(16.dp)
              .padding(end = 8.dp),
            strokeWidth = 2.dp,
            color = MaterialTheme.colorScheme.primary
          )
        }
        Text(
          text = messageText,
          color = messageColor,
          style = MaterialTheme.typography.bodyMedium
        )
      }
    }
  }
}

private fun uriToFile(context: Context, uri: Uri): File {
  val inputStream = context.contentResolver.openInputStream(uri)
  val tempFile = File(context.cacheDir, "vovotapesa_${System.currentTimeMillis()}.jpg")
  inputStream?.use { input ->
    tempFile.outputStream().use { output ->
      input.copyTo(output)
    }
  }
  return tempFile
}