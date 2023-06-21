package com.example.chatapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.data.Response
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SplashScreenViewModel @Inject
constructor() : ViewModel() {

    private val _isUserLogged = MutableLiveData<Boolean>()
    val isUserLogged: LiveData<Boolean> = _isUserLogged

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun checkUser() {
        _isUserLogged.value = auth.currentUser != null
    }


}
