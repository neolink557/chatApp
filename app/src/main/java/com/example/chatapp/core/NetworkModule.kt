package com.example.chatapp.core

import com.example.chatapp.data.repositories.remoteRepository.RemoteRepository
import com.example.chatapp.data.repositories.remoteRepository.RemoteRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideRepository(firebaseAuth: FirebaseAuth,firebaseDatabase: FirebaseDatabase): RemoteRepository {
        return RemoteRepositoryImpl(firebaseAuth,firebaseDatabase)
    }
}