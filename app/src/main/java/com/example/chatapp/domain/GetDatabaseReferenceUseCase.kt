package com.example.chatapp.domain

import com.example.chatapp.data.repositories.remoteRepository.RemoteRepository
import javax.inject.Inject


class GetDatabaseReferenceUseCase @Inject constructor(private val remoteRepository: RemoteRepository){
    operator fun invoke(path:String) = remoteRepository.getDatabaseReference(path)
}