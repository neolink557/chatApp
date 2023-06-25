package com.example.chatapp.domain

import com.example.chatapp.data.repositories.remoteRepository.RemoteRepository
import javax.inject.Inject


class GetCurrentUserUseCase @Inject constructor(private val remoteRepository: RemoteRepository){
    operator fun invoke() = remoteRepository.getCurrentUser()
}