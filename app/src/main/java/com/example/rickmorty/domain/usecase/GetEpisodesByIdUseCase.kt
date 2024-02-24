package com.example.rickmorty.domain.usecase

import com.example.rickmorty.common.Resource
import com.example.rickmorty.domain.model.Episode
import com.example.rickmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class GetEpisodesByIdUseCase @Inject constructor(
    private val repository: RickAndMortyRepository
) {
    suspend operator fun invoke(episodeIds: List<Int>, characterId: Int): Resource<List<Episode>> {
        return repository.fetchEpisodes(episodeIds = episodeIds, characterId = characterId)
    }
}