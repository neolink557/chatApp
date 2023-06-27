package com.example.chatapp.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.data.models.Message
import com.example.chatapp.data.models.MessageData
import com.example.chatapp.data.models.User
import com.example.chatapp.domain.GetChatUseCase
import com.example.chatapp.domain.GetCurrentUserUseCase
import com.example.chatapp.domain.GetDatabaseReferenceUseCase
import com.example.chatapp.domain.SendMessageUseCase
import com.example.chatapp.utils.TIME_FORMAT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getChatUseCase: GetChatUseCase
) : ViewModel() {

    private val _chatList = MutableLiveData<List<Message>>()
    val chatList: LiveData<List<Message>> = _chatList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun sendMessage(receiver: User, message: String) {
        val date = DateTimeFormatter
            .ofPattern(TIME_FORMAT)
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())

        val currentUser = getCurrentUserUseCase()

        if (currentUser != null && !receiver.userId.isNullOrEmpty() && !receiver.username.isNullOrEmpty()) {
            val cid = listOf(currentUser.uid, receiver.userId).sortedBy { it }.joinToString("-")
            val msg = MessageData(
                sender = User(userId = currentUser.uid, username = currentUser.email),
                receiver = receiver,
                message = message,
                timestamp = date.toString(),
                chatId = cid
            )
            sendMessageUseCase(Message(msg)).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    _errorMessage.postValue(task.exception?.message)
                } else {
                    getChat(receiver.userId!!)
                }
            }
        }
    }

    fun getChat(receiverId: String) {
        getCurrentUserUseCase()?.let { user ->
            val cid = listOf(user.uid, receiverId).sortedBy { it }.joinToString("-")
            getChatUseCase(cid).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Message>()
                    for (data in snapshot.children) {
                        val message = data.getValue(Message::class.java)
                        message?.let {
                            list.add(message)
                        }
                    }
                    _chatList.postValue(list.reversed())
                }

                override fun onCancelled(error: DatabaseError) {
                    _errorMessage.postValue(error.message)
                }
            })
        }
    }

}
