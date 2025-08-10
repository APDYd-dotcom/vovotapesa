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
import com.example.vovotapesa.ui.app.screens.ProfileScreen
import com.example.vovotapesa.ui.app.screens.SignUpScreen
import com.example.vovotapesa.viewmodel.AuthViewModel


sealed class Rooter{
  data class Login(val name: String="login"): Rooter()
  data class SignUp(val name: String="signUp"): Rooter()
  data class Home(val name: String="Home"): Rooter()
  data class Profile(val name: String="Profile"): Rooter()
}

sealed class PageRooter{
  data class Send(val name: String="send"): PageRooter()
  data class Wallet(val name: String="wallet"): PageRooter()
  data class Alerts(val name: String="alerts"): PageRooter()
}

@Composable
fun MyNavigation(
  navHostController: NavHostController,
  authViewModel: AuthViewModel = hiltViewModel()
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
        onProfileClick = { navHostController.navigate(Rooter.Profile().name) }
      )
    }
    composable(route = Rooter.Profile().name) {
      ProfileScreen(
        onLogoutClick = { navHostController.navigate(Rooter.Login().name)},
        authViewModel = authViewModel,
        navController = navHostController
      )
    }
  }
}


@Composable
fun MyPageNavigation(navHostController: NavHostController){
  NavHost(
    navController = navHostController,
    startDestination = PageRooter.Wallet().name
  ){
    composable(route= PageRooter.Wallet().name) {
      WalletPage()
    }
    composable(route= PageRooter.Send().name) {
      SendPage()
    }
    composable(route= PageRooter.Alerts().name) {
      AlertsPage()
    }
  }

}