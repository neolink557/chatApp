package com.example.chatapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.ui.chat.ChatScreen
import com.example.chatapp.ui.login.LoginViewModel
import com.example.chatapp.ui.home.HomeScreen
import com.example.chatapp.ui.home.HomeViewModel
import com.example.chatapp.ui.login.LoginScreen
import com.example.chatapp.ui.splash.SplashScreen
import com.example.chatapp.ui.splash.SplashViewModel

@Composable
fun AppNavigation(viewModels: Map<String, ViewModel>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController = navController, viewModels[Screens.SplashScreen.route] as SplashViewModel)
        }
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController = navController, viewModels[Screens.LoginScreen.route] as LoginViewModel)
        }
        composable(Screens.HomeScreen.route) {
            HomeScreen(navController = navController, viewModels[Screens.HomeScreen.route] as HomeViewModel)
        }
        composable(Screens.ChatScreen.route) {
            ChatScreen(navController = navController)
        }
    }
}