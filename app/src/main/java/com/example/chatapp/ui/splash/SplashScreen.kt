package com.example.chatapp.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.example.chatapp.R
import com.example.chatapp.ui.navigation.Screens
import com.example.chatapp.ui.theme.Red
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true){
        delay(3700)
        navController.popBackStack()
        navController.navigate(Screens.LoginScreen.route)
    }
    SplashScreenLayout()
}

@Composable
fun SplashScreenLayout(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Red),
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