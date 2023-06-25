package com.example.chatapp.core

import com.example.chatapp.data.repositories.remoteRepository.RemoteRepository
import com.example.chatapp.data.repositories.remoteRepository.RemoteRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideRepository(firebaseAuth: FirebaseAuth): RemoteRepository {
        return RemoteRepositoryImpl(firebaseAuth)
    }
}