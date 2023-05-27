package com.example.chatapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.LoginViewModel
import com.example.chatapp.ui.login.LoginScreen
import com.example.chatapp.ui.splash.SplashScreen

@Composable
fun AppNavigation(viewModels: Map<String, ViewModel>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController = navController, viewModels[Screens.LoginScreen.route] as LoginViewModel)
        }
    }
}