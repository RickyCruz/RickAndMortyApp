package com.example.rickmorty.di

import com.example.rickmorty.data.repository.RickAndMortyRepositoryImpl
import com.example.rickmorty.domain.repository.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRickAndMortyRepository(
        repositoryImplementation: RickAndMortyRepositoryImpl
    ): RickAndMortyRepository

}