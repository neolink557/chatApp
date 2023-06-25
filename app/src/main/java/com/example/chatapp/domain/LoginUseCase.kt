package com.example.chatapp.domain

import com.example.chatapp.data.repositories.remoteRepository.RemoteRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val remoteRepository: RemoteRepository){
    operator fun invoke(email:String,password:String) = remoteRepository.login(email, password)
}