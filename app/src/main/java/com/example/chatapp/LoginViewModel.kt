package com.example.chatapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.data.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    private val _loginStatus = MutableLiveData<Response>()
    val loginStatus: LiveData<Response> = _loginStatus

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signIn(username: String, password: String) {
        auth.createUserWithEmailAndPassword(username.trim(), password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val data: MutableMap<String, String> = mutableMapOf()
                    val userId = auth.currentUser?.uid ?: ""
                    val databaseReference =
                        FirebaseDatabase.getInstance().getReference("users").child(userId)

                    data["username"] = username
                    data["userId"] = userId

                    databaseReference.setValue(data).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _loginStatus.value = Response.Success()
                        } else {
                            when (task.exception?.message) {
                                "The email address is already in use by another account" -> login(username, password)
                                else -> _loginStatus.value = Response.Error(task.exception?.message)
                            }
                        }
                    }
                } else {
                    when (task.exception?.message) {
                        "The email address is already in use by another account." -> login(username, password)
                        else -> _loginStatus.value = Response.Error(task.exception?.message)
                    }
                }
            }
    }

    private fun login(username: String, password: String) {
        auth.signInWithEmailAndPassword(
            username.trim(),
            password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _loginStatus.value = Response.Success()
            } else {
                _loginStatus.value = Response.Error(task.exception?.message)
            }
        }
    }

}