package com.example.chatapp.ui.navigation

import com.example.chatapp.utils.CHAT_SCREEN
import com.example.chatapp.utils.LOGIN_SCREEN
import com.example.chatapp.utils.SPLASH_SCREEN

sealed class Screens(val route: String) {
    object SplashScreen : Screens(SPLASH_SCREEN)
    object LoginScreen : Screens(LOGIN_SCREEN)
    object ChatScreen : Screens(CHAT_SCREEN)
}