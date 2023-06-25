package com.example.chatapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.data.Response
import com.example.chatapp.domain.CreateUserUseCase
import com.example.chatapp.domain.GetCurrentUserUseCase
import com.example.chatapp.domain.GetDatabaseReferenceUseCase
import com.example.chatapp.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getDatabaseReferenceUseCase: GetDatabaseReferenceUseCase,
    private val loginUseCase: LoginUseCase
) :
    ViewModel() {

    private val _loginStatus = MutableLiveData<Response>()
    val loginStatus: LiveData<Response> = _loginStatus

    fun signIn(email: String, password: String) {
        createUserUseCase(email.trim(), password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val data: MutableMap<String, String> = mutableMapOf()
                    val userId = getCurrentUserUseCase()?.uid ?: ""
                    val databaseReference = getDatabaseReferenceUseCase("users").child(userId)

                    data["username"] = email
                    data["userId"] = userId

                    databaseReference.setValue(data).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _loginStatus.value = Response.Success()
                        } else {
                            when (task.exception?.message) {
                                "The email address is already in use by another account" -> login(
                                    email,
                                    password
                                )
                                else -> _loginStatus.value = Response.Error(task.exception?.message)
                            }
                        }
                    }
                } else {
                    when (task.exception?.message) {
                        "The email address is already in use by another account." -> login(
                            email,
                            password
                        )
                        else -> _loginStatus.value = Response.Error(task.exception?.message)
                    }
                }
            }
    }

    private fun login(email: String, password: String) {
        loginUseCase(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _loginStatus.value = Response.Success()
            } else {
                _loginStatus.value = Response.Error(task.exception?.message)
            }
        }
    }

}