package com.example.chatapp.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.example.chatapp.R
import com.example.chatapp.ui.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, viewModel: SplashViewModel) {
    viewModel.checkUser()
    val loginStatus: Boolean? by viewModel.isUserLogged.observeAsState(null)
    LaunchedEffect(key1 = true) {
        delay(3700)
        if (loginStatus == true) {
            navController.popBackStack()
            navController.navigate(Screens.HomeScreen.route)
        } else {
            navController.popBackStack()
            navController.navigate(Screens.LoginScreen.route)
        }
    }
    SplashScreenLayout()
}

@Composable
fun SplashScreenLayout() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Loader()
    }
}

@Composable
fun Loader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    LottieAnimation(
        modifier = Modifier.size(300.dp),
        composition = composition,
        progress = { progress },
    )
}