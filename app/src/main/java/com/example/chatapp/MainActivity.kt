package com.example.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.navigation.AppNavigation
import com.example.chatapp.ui.navigation.Screens
import com.example.chatapp.ui.splash.SplashScreenViewModel
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.ui.theme.Yellow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val splashScreenViewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vmDictionary = inflateDictionary()


        setContent {
            ChatAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Yellow
                ) {
                    AppNavigation(vmDictionary)
                }
            }
        }
    }

    private fun inflateDictionary(): Map<String, ViewModel> {
        val vmDictionary = mutableMapOf<String, ViewModel>()
        vmDictionary[Screens.LoginScreen.route] = loginViewModel
        vmDictionary[Screens.SplashScreen.route] = splashScreenViewModel
        return vmDictionary
    }
}

