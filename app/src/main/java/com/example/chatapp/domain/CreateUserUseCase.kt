package com.example.chatapp.domain

import com.example.chatapp.data.repositories.remoteRepository.RemoteRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


class CreateUserUseCase @Inject constructor(private val remoteRepository: RemoteRepository){
    operator fun invoke(email:String,password:String) = remoteRepository.createUser(email, password)
}