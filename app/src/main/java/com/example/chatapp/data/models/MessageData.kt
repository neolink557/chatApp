package com.example.chatapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageData(
    var chatId : String? = null,
    var message: String? = null,
    var sender: User? = null,
    var receiver: User? = null,
    var timestamp: String? = null,
) : Parcelable