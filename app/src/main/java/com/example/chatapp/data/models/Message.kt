package com.example.chatapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    var message: MessageData? = null
) : Parcelable