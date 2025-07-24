package com.example.vovotapesa.ui.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vovotapesa.ui.app.pages.AlertsPage
import com.example.vovotapesa.ui.app.pages.HomePage
import com.example.vovotapesa.ui.app.pages.SendPage
import com.example.vovotapesa.ui.app.pages.WalletPage
import com.example.vovotapesa.ui.app.pages.WithdrawPage
import com.example.vovotapesa.ui.app.screens.HomeScreen
import com.example.vovotapesa.ui.app.screens.LoginScreen
import com.example.vovotapesa.ui.app.screens.ProfileScreen
import com.example.vovotapesa.ui.app.screens.SignUpScreen

sealed class Rooter{
  data class Login(val name: String="login"): Rooter()
  data class SignUp(val name: String="signUp"): Rooter()
  data class Home(val name: String="Home"): Rooter()
  data class Profile(val name: String="Profile"): Rooter()
}

sealed class PageRooter{
  data class Home(val name: String="home"): PageRooter()
  data class Withdraw(val name: String="withdraw"): PageRooter()
  data class Send(val name: String="send"): PageRooter()
  data class Wallet(val name: String="wallet"): PageRooter()
  data class Alerts(val name: String="alerts"): PageRooter()
}

@Composable
fun MyNavigation(navHostController: NavHostController){
  NavHost(
    navController = navHostController,
    startDestination = Rooter.Login().name
  ){
    composable(route = Rooter.Login().name) {
      LoginScreen()
    }
    composable(route = Rooter.SignUp().name) {
      SignUpScreen()
    }
    composable(route = Rooter.Home().name) {
      HomeScreen()
    }
    composable(route = Rooter.Profile().name) {
      ProfileScreen()
    }
  }
}


@Composable
fun MyPageNavigation(navHostController: NavHostController){
  NavHost(
    navController = navHostController,
    startDestination = PageRooter.Home().name
  ){
    composable(route= PageRooter.Home().name) {
      HomePage()
    }
    composable(route= PageRooter.Wallet().name) {
      WalletPage()
    }
    composable(route= PageRooter.Send().name) {
      SendPage()
    }
    composable(route= PageRooter.Withdraw().name) {
      WithdrawPage()
    }
    composable(route= PageRooter.Alerts().name) {
      AlertsPage()
    }
  }

}