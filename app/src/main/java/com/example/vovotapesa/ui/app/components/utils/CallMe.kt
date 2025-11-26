package com.example.vovotapesa.ui.app.components.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.vovotapesa.ui.theme.FontSizes
//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun CallMe(
//  phone: String,
//  prefix: String = "",
//  suffix: String = "",
//) {
//  FlowRow(
//    modifier = Modifier
//      .fillMaxWidth()
//      .padding(horizontal = 24.dp),
//    horizontalArrangement = Arrangement.End,
//    verticalArrangement = Arrangement.Center
//  ){
//    val context = LocalContext.current
//
//    val annotatedText = buildAnnotatedString {
//      append(prefix)
//      pushStringAnnotation(tag = "CALL", annotation = "tel:$phone")
//      withStyle(
//        style = SpanStyle(
//          color = MaterialTheme.colorScheme.primary,
//          textDecoration = TextDecoration.Underline,
//        )
//      ) {
//        append(suffix)
//      }
//      pop()
//    }
//
//    ClickableText(
//      text = annotatedText,
//      onClick = { callPhone(phone, context) },
//      style = MaterialTheme.typography.bodyMedium.copy(fontSize = FontSizes.caption())
//    )
//  }
//}
//
