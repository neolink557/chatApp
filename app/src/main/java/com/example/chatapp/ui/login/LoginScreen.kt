package com.example.chatapp.ui.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.chatapp.LoginViewModel
import com.example.chatapp.data.Response
import com.example.chatapp.ui.theme.LightGrey
import com.example.chatapp.ui.theme.Red
import com.example.chatapp.ui.theme.White
import javax.inject.Inject

private lateinit var viewModel: LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController?, loginViewModel: LoginViewModel) {
    viewModel = loginViewModel

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginStatus:Response? by viewModel.loginStatus.observeAsState(null)

    when(loginStatus) {
        is Response.Loading -> {Toast.makeText(LocalContext.current, "Loading", Toast.LENGTH_SHORT).show()}
        is Response.Error -> {Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()}
        is Response.Success -> {Toast.makeText(LocalContext.current, "Success", Toast.LENGTH_SHORT).show()}
        null -> {}
    }

    navController?.let {
        LoginScreenLayout(
            navController,
            username,
            password,
            onUsernameChanged = { username = it },
            onPasswordChanged = { password = it },
            onClick = { viewModel.login(username, password) }
        )
    }
}

@Composable
private fun LoginScreenLayout(
    navController: NavHostController,
    username: String,
    password: String,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Red)
    ) {
        val (LoginText, LoginCard) = createRefs()
        Text(
            text = "Welcome Back!",
            color = White,
            style = TextStyle(
                fontSize = 50.sp,
                fontWeight = FontWeight(200)
            ),
            modifier = Modifier
                .padding(20.dp)
                .width(250.dp)
                .constrainAs(LoginText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(LoginCard.top)
                    start.linkTo(parent.start)
                }
        )
        LoginCard(Modifier.constrainAs(LoginCard) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }, username, password, onUsernameChanged, onPasswordChanged, onClick)
    }
}

@Composable
private fun LoginCard(
    modifier: Modifier,
    username: String,
    password: String,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxHeight(0.70f)
            .fillMaxWidth(),
        backgroundColor = White,
        shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp),
        elevation = 20.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            LoginTextField("Username", username, onUsernameChanged)
            Spacer(modifier = Modifier.height(20.dp))
            LoginTextField("Password", password, onPasswordChanged)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Forgot your password?",
                style = TextStyle(
                    fontWeight = FontWeight(400),
                    fontSize = 14.sp
                ),
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
            Spacer(modifier = Modifier.height(60.dp))
            LoginButton(placeholder = "SIGN IN", onClick)
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("Dont have an account?, ")
                    }
                    withStyle(style = SpanStyle(color = Red)) {
                        append("sign up now!")
                    }
                },
                style = TextStyle(
                    fontWeight = FontWeight(400),
                    fontSize = 14.sp
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun LoginTextField(placeholder: String, text: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = text,
        shape = RoundedCornerShape(16.dp),
        placeholder = { Text(text = placeholder) },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(fontWeight = FontWeight(450), color = Color.Black),
        singleLine = true,
        onValueChange = { text: String -> onValueChanged(text) },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Red,
            backgroundColor = LightGrey,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = Color.Gray,
            textColor = Color.Black
        )
    )
}

@Composable
private fun LoginButton(placeholder: String, onClick: () -> Unit = {}) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Red,
            contentColor = White
        ),
    ) {
        Text(
            text = placeholder,
            style = TextStyle(
                fontWeight = FontWeight(400),
                letterSpacing = 1.sp
            )
        )
    }
}