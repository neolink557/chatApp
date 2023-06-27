package com.example.chatapp.data.repositories.remoteRepository

import com.example.chatapp.data.models.Message
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

interface RemoteRepository {
    fun createUser(email: String, password: String): Task<AuthResult>
    fun getCurrentUser(): FirebaseUser?
    fun getDatabaseReference(path: String): DatabaseReference
    fun login(email: String, password: String): Task<AuthResult>
    fun sendMessage(message: Message): Task<Void>
    fun getChat(chatId:String): Query
}