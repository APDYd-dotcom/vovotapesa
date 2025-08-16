package com.example.vovotapesa.ui.app.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vovotapesa.ui.app.pages.AlertsPage
import com.example.vovotapesa.ui.app.pages.SendPage
import com.example.vovotapesa.ui.app.pages.WalletPage
import com.example.vovotapesa.ui.app.screens.HomeScreen
import com.example.vovotapesa.ui.app.screens.LoginScreen
import com.example.vovotapesa.ui.app.pages.ProfileScreen
import com.example.vovotapesa.ui.app.screens.SignUpScreen
import com.example.vovotapesa.viewmodel.AuthViewModel
import com.example.vovotapesa.viewmodel.NotificationViewModel
import com.example.vovotapesa.viewmodel.ProfileViewModel
import com.example.vovotapesa.viewmodel.TransactionViewModel
import com.example.vovotapesa.viewmodel.WalletViewModel


sealed class Rooter{
  data class Login(val name: String="login"): Rooter()
  data class SignUp(val name: String="signUp"): Rooter()
  data class Home(val name: String="Home"): Rooter()
}

sealed class PageRooter{
  data class Send(val name: String="send"): PageRooter()
  data class Wallet(val name: String="wallet"): PageRooter()
  data class Alerts(val name: String="alerts"): PageRooter()
  data class Profile(val name: String="Profile"): Rooter()
}

@Composable
fun MyNavigation(
  navHostController: NavHostController,
  authViewModel: AuthViewModel = hiltViewModel(),
  walletViewModel: WalletViewModel = hiltViewModel(),
  profileViewModel: ProfileViewModel = hiltViewModel(),
  notificationViewModel: NotificationViewModel = hiltViewModel(),
  transactionViewModel: TransactionViewModel = hiltViewModel()
){


  NavHost(
    navController = navHostController,
    startDestination = Rooter.Login().name
  ){
    composable(route = Rooter.Login().name) {
      LoginScreen(
        onLoginClick ={navHostController.navigate(Rooter.Home().name)},
        onSignupClick = {navHostController.navigate(Rooter.SignUp().name)},
        authViewModel = authViewModel
      )
    }
    composable(route = Rooter.SignUp().name) {
      SignUpScreen(
        authViewModel = authViewModel,
        onLoginClick = {navHostController.navigate(Rooter.Login().name)}
      )
    }
    composable(route = Rooter.Home().name) {
      HomeScreen(
        authViewModel = authViewModel,
        walletViewModel = walletViewModel,
        profileViewModel = profileViewModel,
        notificationViewModel = notificationViewModel,
        transactionViewModel = transactionViewModel
      )
    }
  }
}


@Composable
fun MyPageNavigation(
  navHostController: NavHostController,
  authViewModel: AuthViewModel,
  profileViewModel: ProfileViewModel,
  walletViewModel: WalletViewModel,
  notificationViewModel: NotificationViewModel,
  transactionViewModel: TransactionViewModel
){
  NavHost(
    navController = navHostController,
    startDestination = PageRooter.Wallet().name
  ){
    composable(route= PageRooter.Wallet().name) {
      WalletPage(
        walletViewModel = walletViewModel,
        authViewModel = authViewModel,
        transactionViewModel = transactionViewModel
      )
    }
    composable(route= PageRooter.Send().name) {
      SendPage(
        transactionViewModel = transactionViewModel,
        authViewModel = authViewModel
      )
    }
    composable(route= PageRooter.Alerts().name) {
      AlertsPage(
        notificationViewModel = notificationViewModel,
        authViewModel = authViewModel
      )
    }
    composable(route = PageRooter.Profile().name) {
      ProfileScreen(
        onLogoutClick = { navHostController.navigate(Rooter.Login().name) },
        navController = navHostController,
        authViewModel = authViewModel,
        profileViewModel = profileViewModel
      )
    }
  }


}