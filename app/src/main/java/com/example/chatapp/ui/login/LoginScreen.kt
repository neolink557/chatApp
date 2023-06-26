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
import com.example.chatapp.data.Response
import com.example.chatapp.ui.navigation.Screens
import com.example.chatapp.ui.theme.Yellow
import com.example.chatapp.ui.theme.White

private lateinit var viewModel: LoginViewModel
private const val TEXT_SCALE_REDUCTION_INTERVAL = 0.9f

@Composable
fun LoginScreen(navController: NavHostController?, loginViewModel: LoginViewModel) {
    viewModel = loginViewModel

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginStatus: Response? by viewModel.loginStatus.observeAsState(null)

    when (loginStatus) {
        is Response.Loading -> {
            Toast.makeText(LocalContext.current, "Loading", Toast.LENGTH_SHORT).show()
        }
        is Response.Error -> {
            Toast.makeText(LocalContext.current, (loginStatus as Response.Error).message, Toast.LENGTH_SHORT).show()
        }
        is Response.Success -> {
            LaunchedEffect(Unit) {
                navController?.navigate(Screens.HomeScreen.route) {
                    popUpTo(Screens.LoginScreen.route) { inclusive = true }
                }
            }
        }
        null -> {}
    }

    navController?.let {
        LoginScreenLayout(
            username,
            password,
            onUsernameChanged = { username = it },
            onPasswordChanged = { password = it },
            onClick = { viewModel.signIn(username, password) }
        )
    }
}

@Composable
private fun LoginScreenLayout(
    username: String,
    password: String,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClick: () -> Unit
) {
    var textSize by remember { mutableStateOf(50.sp) }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        val (LoginText, LoginCard) = createRefs()
        Text(
            text = "Welcome Back!",
            color = MaterialTheme.colors.secondary,
            maxLines = 2,
            style = TextStyle(
                fontSize = textSize,
                fontWeight = FontWeight(400)
            ),
            modifier = Modifier
                .padding(20.dp)
                .width(250.dp)
                .constrainAs(LoginText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(LoginCard.top)
                    start.linkTo(parent.start)
                },
            onTextLayout = { textLayoutResult ->
                val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1
                if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                    textSize = textSize.times(TEXT_SCALE_REDUCTION_INTERVAL)
                }
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
        backgroundColor = MaterialTheme.colors.background,
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
                    withStyle(style = SpanStyle(color = Yellow)) {
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
        textStyle = TextStyle(fontWeight = FontWeight(450), color = MaterialTheme.colors.onBackground),
        singleLine = true,
        onValueChange = { text: String -> onValueChanged(text) },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Yellow,
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
            textColor = MaterialTheme.colors.onBackground
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
            backgroundColor = Yellow,
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