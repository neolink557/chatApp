package com.example.chatapp.data.repositories.remoteRepository

import com.example.chatapp.data.models.Message
import com.example.chatapp.utils.CHAT_PATHSTRING
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDB: FirebaseDatabase
) : RemoteRepository {

    override fun createUser(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun getDatabaseReference(path: String): DatabaseReference {
        return firebaseDB.getReference(path)
    }

    override fun login(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(
            email.trim(),
            password
        )
    }

    override fun sendMessage(message: Message): Task<Void> {
        return firebaseDB.reference.child(CHAT_PATHSTRING).push().setValue(message)
    }

    override fun getChat(chatId: String): Query {
        return firebaseDB.reference.child(CHAT_PATHSTRING).orderByChild("message/chatId")
            .equalTo(chatId)
    }
}