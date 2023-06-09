package com.example.chatapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var userId: String? = null,
    var username: String? = null
) : Parcelable
