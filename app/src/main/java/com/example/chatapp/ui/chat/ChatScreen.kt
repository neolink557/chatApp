package com.example.chatapp.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.chatapp.ui.theme.Yellow
import java.util.*


@Composable
fun ChatScreen(navController: NavHostController) {
    val username = "username"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        ChatScreenLayout(username = username)
    }
}

@Composable
private fun ChatScreenLayout(
    username: String
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        val (LoginText, LoginCard) = createRefs()
        Text(
            text = username.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.secondary,
            maxLines = 1,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight(400)
            ),
            modifier = Modifier
                .width(250.dp)
                .constrainAs(LoginText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(LoginCard.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        ChatCard(Modifier.constrainAs(LoginCard) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        })
    }
}

@Composable
private fun ChatCard(
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp),
        elevation = 20.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChatTextField(placeholder = "Text here", text = "", onValueChanged ={} )
        }
    }
}

@Composable
private fun ChatTextField(placeholder: String, text: String, onValueChanged: (String) -> Unit) {
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
@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreenLayout(username = "username")
}