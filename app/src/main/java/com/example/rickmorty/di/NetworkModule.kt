package com.example.rickmorty.di

import com.example.rickmorty.data.remote.KtorClient
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
    fun providesKtorClient(): KtorClient {
        return KtorClient()
    }
}