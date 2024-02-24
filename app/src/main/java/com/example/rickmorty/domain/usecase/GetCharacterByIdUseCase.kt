package com.example.rickmorty.domain.usecase

import com.example.rickmorty.common.Resource
import com.example.rickmorty.domain.model.Character
import com.example.rickmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val repository: RickAndMortyRepository
) {
    suspend operator fun invoke(characterId: Int): Resource<Character> {
        return repository.fetchCharacter(characterId = characterId)
    }
}