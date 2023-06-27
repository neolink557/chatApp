package com.example.chatapp.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.data.models.Message
import com.example.chatapp.data.models.MessageData
import com.example.chatapp.data.models.User
import com.example.chatapp.ui.theme.Yellow
import kotlinx.coroutines.launch
import java.util.*

private lateinit var viewModel: ChatViewModel
private var receiver: User? = null

@Composable
fun ChatScreen(navController: NavHostController, vm: ChatViewModel, r: User?) {
    val username = receiver?.username ?: ""
    receiver = r
    viewModel = vm
    receiver?.userId?.let { viewModel.getChat(it) }

    val contactsList by viewModel.chatList.observeAsState(null)
    var message by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        ChatScreenLayout(
            contactsList,
            username = username,
            message = message
        ) { message = it }
    }
}

@Composable
private fun ChatScreenLayout(
    contactsList: List<Message>?,
    username: String,
    message: String,
    onMessageChanged: (String) -> Unit
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
        ChatCard(
            Modifier.constrainAs(LoginCard) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            contactsList,
            message,
            onMessageChanged

        )
    }
}

@Composable
private fun ChatCard(
    modifier: Modifier,
    contactsList: List<Message>?,
    message: String,
    onMessageChanged: (String) -> Unit
) {
    val lazyColumnListState = rememberLazyListState()
    val corroutineScope = rememberCoroutineScope()
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(0.9F),
                state = lazyColumnListState,
                reverseLayout = true
            ) {
                contactsList?.let {
                    corroutineScope.launch {
                        if (it.isNotEmpty()) lazyColumnListState.scrollToItem(it.size - 1)
                    }
                    items(it.size) { index ->
                        it[index].message?.let { messageData ->
                            ChatItem(
                                message = messageData,
                                isMe = it[index].message?.chatId?.split("-")
                                    ?.get(0) == it[index].message?.sender?.userId
                            )
                        }
                    }
                }

            }
            ChatTextField(message = message, onValueChanged = onMessageChanged)
        }
    }
}

@Composable
fun ChatItem(message: MessageData, isMe: Boolean) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val (messageText, messageCard) = createRefs()
        Card(
            modifier = Modifier
                .constrainAs(messageCard) {
                    if (isMe) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    } else {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                }
                .width(250.dp),
            backgroundColor = if (isMe) MaterialTheme.colors.secondary else MaterialTheme.colors.primary.copy(alpha = 0.5f),
            shape = RoundedCornerShape(20.dp),
            elevation = 5.dp
        ) {
            Text(
                text = message.message ?: "",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400)
                ),
                modifier = Modifier
                    .constrainAs(messageText) {
                        if (isMe) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        } else {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                    }
                    .width(250.dp)
                    .padding(5.dp),
                color = MaterialTheme.colors.onBackground
            )
        }
    }

}

@Composable
private fun ChatTextField(message: String, onValueChanged: (String) -> Unit) {
    TextField(
        value = message,
        trailingIcon = {
            IconButton(onClick = {
                sendMessage(message)
                onValueChanged("")
            }) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send",
                    tint = MaterialTheme.colors.secondary
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        placeholder = { Text(text = stringResource(R.string.text_message_placeholder)) },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(
            fontWeight = FontWeight(450),
            color = MaterialTheme.colors.onBackground
        ),
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

fun sendMessage(message: String) {
    receiver?.let {
        if (message.isNotEmpty()) viewModel.sendMessage(it, message)
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreenLayout(listOf(), username = "username", message = "message") { }
}