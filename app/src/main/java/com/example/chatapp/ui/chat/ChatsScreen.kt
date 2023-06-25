package com.example.chatapp.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.data.models.User
import com.example.chatapp.ui.theme.*

private lateinit var viewModel: ChatViewModel

@Composable
fun ChatScreen(navController: NavHostController, chatViewModel: ChatViewModel) {
    viewModel = chatViewModel
    viewModel.getContactsList()
    val contactsList by viewModel.contactsList.observeAsState(null)
    ChatScreenLayout(contactsList)
}

@Composable
private fun ChatScreenLayout(contactsList: List<User>?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column() {
            HeaderCard()
            contactsList?.let { contactsList ->
                if (contactsList.isEmpty()) {
                    NoContactsImage(Modifier.align(Alignment.CenterHorizontally))
                } else {
                    ContactsListLayout(contactsList)
                }
            }
        }

        FloatingActionButton(
            onClick = { /*TODO*/ },
            backgroundColor = Yellow,
            contentColor = White,
            content = {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "new chat",
                    tint = Color.White
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        )
    }
}

@Composable
fun ContactsListLayout(contactsList: List<User>) {
    LazyColumn(content = {
        items(contactsList.size) { index ->
            ContactCard(contactsList[index])
        }
    })
}

@Composable
fun ContactCard(user: User) {
    Text(
        text = user.username ?: "No name",
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight(400),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        ),
    )
}


@Composable
private fun NoContactsImage(modifier: Modifier) {
    Spacer(modifier = modifier.height(90.dp))
    Image(
        painter = painterResource(id = R.drawable.bee),
        contentDescription = "Bzzz there's no chats",
        alpha = if (isSystemInDarkTheme()) 0.5f else 0.9f,
        modifier = modifier
            .height(300.dp)
            .width(300.dp)
    )
    Text(
        text = "Bzzz there's no chats, click the button below to start a new chat",
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight(400),
            textAlign = TextAlign.Center
        ),
        modifier = modifier
            .padding(horizontal = 80.dp)
    )
}

@Composable
private fun HeaderCard() {
    Card(
        modifier = Modifier
            .fillMaxHeight(0.20f)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(bottomEnd = 35.dp, bottomStart = 35.dp),
        elevation = 20.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = "Message",
                color = MaterialTheme.colors.secondary,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight(400)
                ),
                modifier = Modifier
                    .padding(20.dp)
                    .width(250.dp)
            )

            FinderTextField(
                fontSize = 14.sp,
                placeholderText = "Search In Messages",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                    )
                })

        }
    }
}

@Composable
private fun FinderTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize
) {
    var text by remember { mutableStateOf("") }
    BasicTextField(modifier = modifier
        .padding(horizontal = 14.dp)
        .background(
            MaterialTheme.colors.primaryVariant,
            RoundedCornerShape(50),
        )
        .fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colors.secondary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onBackground,
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null)
                    leadingIcon()
                Box(
                    Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    if (text.isEmpty())
                        Text(
                            text = placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                fontSize = fontSize
                            )
                        )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreenLayout(listOf())
}