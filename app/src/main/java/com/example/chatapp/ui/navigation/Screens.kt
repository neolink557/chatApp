package com.example.chatapp.ui.navigation

import com.example.chatapp.data.models.User
import com.example.chatapp.utils.CHAT_SCREEN
import com.example.chatapp.utils.HOME_SCREEN
import com.example.chatapp.utils.LOGIN_SCREEN
import com.example.chatapp.utils.SPLASH_SCREEN

sealed class Screens(val route: String) {
    object SplashScreen : Screens(SPLASH_SCREEN)
    object LoginScreen : Screens(LOGIN_SCREEN)
    object HomeScreen : Screens(HOME_SCREEN)
    object ChatScreen : Screens(CHAT_SCREEN){
        fun createRoute(receiverId: String?,receiverUsername:String?) = "chat_screen/$receiverId/$receiverUsername"
    }
}