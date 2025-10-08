//package com.example.vovotapesa.ui.app.components
//
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.collectAsState
//import androidx.navigation.NavController
//import com.example.vovotapesa.viewmodel.TransactionViewModel
//
//@Composable
//fun GlobalTransactionDialog(
//  transactionViewModel: TransactionViewModel,
//  navController: NavController
//) {
//  val showDialog by transactionViewModel.showDialog.collectAsState()
//
//  if (showDialog) {
//    AlertDialog(
//      onDismissRequest = { transactionViewModel.hideTransactionDialog() },
//      title = { Text("Transaction Sent") },
//      text = { Text("Your transaction request has been sent!") },
//      confirmButton = {
//        Button(onClick = {
//          transactionViewModel.hideTransactionDialog()
//          navController.navigate("wallet") {
//            popUpTo(0) { inclusive = true } // clear backstack if needed
//          }
//        }) {
//          Text("OK")
//        }
//      }
//    )
//  }
//}
