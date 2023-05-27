package com.example.chatapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.data.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor():ViewModel() {

    private val _loginStatus = MutableLiveData<Response>()
    val loginStatus:LiveData<Response> = _loginStatus

    fun login(username: String, password: String) {
        _loginStatus.postValue(Response.Loading())
        _loginStatus.postValue(Response.Success())
    }
}