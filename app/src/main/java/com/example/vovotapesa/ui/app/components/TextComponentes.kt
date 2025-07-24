package com.example.vovotapesa.ui.app.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.vovotapesa.ui.theme.FontSizes.body
import com.example.vovotapesa.ui.theme.FontSizes.caption
import com.example.vovotapesa.ui.theme.FontSizes.title


/*  Normal Text Component*/
@Composable
fun NormalTextComponent(value: String){
  Text(
    text = value,
    fontSize = caption(),
    fontWeight = FontWeight.Normal,
    fontFamily = FontFamily.Default
  )
}

/*  Normal Text Component*/
@Composable
fun MediumTextComponent(value: String){
  Text(
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
    text = value,
    fontSize = title(),
    fontWeight = FontWeight.Normal,
    fontFamily = FontFamily.Default
  )
}

@Preview(showBackground = true)
@Composable
fun OutPreview(){
  // NormalTextComponent(value = "ter")

  MediumTextComponent(value = "ter")

  // HeaderTextComponent(value = "ter")

}