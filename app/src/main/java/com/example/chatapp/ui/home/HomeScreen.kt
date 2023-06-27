package com.example.chatapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.chatapp.R
import com.example.chatapp.data.models.User
import com.example.chatapp.ui.navigation.Screens
import com.example.chatapp.ui.theme.*
import java.util.*

private lateinit var viewModel: HomeViewModel

@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel) {
    viewModel = homeViewModel
    viewModel.getContactsList()
    var showContactsDialog by remember { mutableStateOf(false) }
    val contactsList by viewModel.contactsList.observeAsState(null)
    HomeScreenLayout(navController,contactsList, showContactsDialog) { showContactsDialog = it }
}

@Composable
private fun HomeScreenLayout(
    navController: NavHostController,
    contactsList: List<User>?,
    showContactsDialog: Boolean,
    setShowContactsDialogState: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        var wordingInfo = ""
        contactsList?.let { contactsList ->
            if (contactsList.isNotEmpty()) {
                wordingInfo = stringResource(R.string.NoChatsWording)
                ContactsDialog(
                    navController = navController,
                    showContactsDialog = showContactsDialog,
                    contactsList = contactsList,
                    onDismiss = { setShowContactsDialogState(false) })
                FloatingActionButton(
                    onClick = { setShowContactsDialogState(true) },
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
            } else {
                wordingInfo = stringResource(R.string.NoContactsWording)
            }
        }


        Column {
            HeaderCard()
            NoContactsImage(Modifier.align(Alignment.CenterHorizontally), wordingInfo)
        }
    }
}

@Composable
private fun ContactsDialog(
    navController: NavHostController,
    showContactsDialog: Boolean,
    contactsList: List<User>,
    onDismiss: () -> Unit
) {
    if (showContactsDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 500.dp),
                backgroundColor = MaterialTheme.colors.background
            ) {
                ContactsListLayout(navController,contactsList = contactsList)
            }
        }
    }
}


@Composable
private fun ContactsListLayout(navController: NavHostController,contactsList: List<User>) {
    Column {
        Text(
            text = "Your Contacts",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )
        Divider(
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            content = {
                items(contactsList.size) { index ->
                    ContactCard(navController,contactsList[index])
                    if (index == contactsList.size - 1) {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            })
    }
}

@Composable
private fun ContactCard(navController: NavHostController,user: User) {
    val username = user.username ?: "OO"
    Row(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = Color.LightGray
                ),
                onClick = {
                    navController.navigate(Screens.ChatScreen.createRoute(user.userId, user.username))
                }
            )
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserTextBubble(user.username ?: "OO")
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = username.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onSurface
                ),
            )
        }
    }
}


@Composable
private fun NoContactsImage(modifier: Modifier, infoWording: String) {
    Spacer(modifier = modifier.height(90.dp))
    Image(
        painter = painterResource(id = R.drawable.bee),
        contentDescription = infoWording,
        alpha = if (isSystemInDarkTheme()) 0.5f else 0.9f,
        modifier = modifier
            .height(300.dp)
            .width(300.dp)
    )
    Text(
        text = infoWording,
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
private fun UserTextBubble(text: String, shape: Shape = CircleShape) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(shape)
            .background(MaterialTheme.colors.primaryVariant)
    ) {
        Text(
            text = text.substring(0, 2)
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            modifier = Modifier.align(Alignment.Center),
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.45f)
        )
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
