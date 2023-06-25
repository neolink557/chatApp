package com.example.chatapp.data.repositories.remoteRepository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : RemoteRepository {

    override fun createUser(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun getDatabaseReference(path:String): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference(path)
    }

    override fun login(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(
            email.trim(),
            password
        )
    }
}