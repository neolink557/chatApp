package com.example.chatapp.domain

import com.example.chatapp.data.models.Message
import com.example.chatapp.data.repositories.remoteRepository.RemoteRepository
import javax.inject.Inject


class SendMessageUseCase @Inject constructor(private val remoteRepository: RemoteRepository){
    operator fun invoke(message: Message) = remoteRepository.sendMessage(message)
}