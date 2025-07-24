package com.example.vovotapesa.ui.app.components

import android.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.vovotapesa.ui.theme.FontSizes.body
import com.example.vovotapesa.ui.theme.FontSizes.caption
import com.example.vovotapesa.ui.theme.FontSizes.title
import java.nio.file.WatchEvent


/*  Normal Text Component*/
@Composable
fun NormalTextComponent(value: String,color: Color){
  Text(
    modifier = Modifier,
    text = value,
    color = color,
    fontSize = caption(),
    fontWeight = FontWeight.Normal,
    fontFamily = FontFamily.Default
  )
}

/*  Normal Text Component*/
@Composable
fun MediumTextComponent(value: String, color: Color){
  Text(
    modifier = Modifier,
    color = color,
    text = value,
    fontSize = body(),
    fontWeight = FontWeight.Normal,
    fontFamily = FontFamily.Default
  )
}

/*  Normal Text Component*/
@Composable
fun HeaderTextComponent(value: String){
  Text(
    modifier = Modifier,
    text = value,
    fontSize = title(),
    fontWeight = FontWeight.Bold,
    fontFamily = FontFamily.SansSerif
  )
}

@Preview(showBackground = true)
@Composable
fun OutPreview(){
  // NormalTextComponent(value = "ter")

//  MediumTextComponent(value = "ter")

  // HeaderTextComponent(value = "ter")

}