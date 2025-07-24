package com.example.vovotapesa.ui.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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