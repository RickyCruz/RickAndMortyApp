package com.example.rickmorty.domain.usecase

import com.example.rickmorty.common.Resource
import com.example.rickmorty.domain.model.Characters
import com.example.rickmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class GetCharactersByPageUseCase @Inject constructor(
    private val repository: RickAndMortyRepository
) {
    suspend operator fun invoke(page: Int): Resource<Characters> {
        return repository.fetchCharacters(page = page)
    }
}