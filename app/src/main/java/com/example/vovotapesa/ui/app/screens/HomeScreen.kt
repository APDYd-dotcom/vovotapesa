package com.example.vovotapesa.ui.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vovotapesa.ui.app.components.NormalTextComponent
import com.example.vovotapesa.ui.app.navigation.MyPageNavigation
import com.example.vovotapesa.R
import com.example.vovotapesa.ui.app.navigation.PageRooter
import com.example.vovotapesa.viewmodel.AuthViewModel
import com.example.vovotapesa.viewmodel.ProfileViewModel
import com.example.vovotapesa.viewmodel.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  profileViewModel: ProfileViewModel,
  walletViewModel: WalletViewModel,
  authViewModel: AuthViewModel
){
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route
  Scaffold(
    topBar = {
      TopAppBar(
//        modifier = Modifier.shadow(elevation = 4.dp), // ✅ Add shadow under the app bar
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.background,
          titleContentColor = MaterialTheme.colorScheme.onBackground
        ),
        title = {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Image(
              painter = painterResource(id = R.drawable.logo),
              contentDescription = "App Logo",
              modifier = Modifier.size(120.dp)
            )

            IconButton(onClick = { navController.navigate(PageRooter.Profile().name)})  { // ✅ Functional click
              Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Profile",
                modifier = Modifier
                  .size(36.dp)
              )
            }
          }
        }
      )
    }
,
    bottomBar = {
      BottomAppBar(
        modifier = Modifier.height(120.dp)
          .shadow(elevation = 8.dp),
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.background
      ) {
        NavigationBar(
          containerColor = MaterialTheme.colorScheme.background,
          tonalElevation = 0.dp
        ) {
          val navItems = listOf(
            Triple(PageRooter.Wallet().name, R.drawable.card, "Wallet"),
            Triple(PageRooter.Send().name, R.drawable.send, "Send"),
            Triple(PageRooter.Alerts().name, R.drawable.alert, "Alerts")
          )

          navItems.forEach { (route, iconId, label) ->
            val isSelected = currentRoute == route
            val selectedBlue = Color(0xFF007BFF) // BlueColor(0xFF008000).copy(alpha = 0.15f)

            NavigationBarItem(
              selected = isSelected,
              onClick = { navController.navigate(route) },
              icon = {
                Box(
                  modifier = Modifier
                    .size(40.dp)
                    .background(
                      if (isSelected) selectedBlue.copy(alpha = 0.15f) else Color.Transparent,
                      shape = CircleShape
                    ),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = label,
                    tint = if (isSelected) selectedBlue else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(24.dp)
                  )
                }
              },
              label = {
                NormalTextComponent(
                  value = label,
                  color = if (isSelected) selectedBlue else MaterialTheme.colorScheme.onBackground
                )
              },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = selectedBlue,
                unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                indicatorColor = Color.Transparent
              )
            )
          }
        }
      }
    }
  ) {innerppading ->
    Column(
      modifier = Modifier.fillMaxSize()
        .padding(innerppading)
    ) {
      MyPageNavigation(
        navController,
        walletViewModel = walletViewModel,
        authViewModel = authViewModel,
        profileViewModel = profileViewModel,
      )
    }
  }
}