package com.example.rickmorty.data.repository

import com.example.rickmorty.common.Resource
import com.example.rickmorty.data.remote.KtorClient
import com.example.rickmorty.domain.model.Character
import com.example.rickmorty.domain.model.Characters
import com.example.rickmorty.domain.model.Episode
import com.example.rickmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val ktorClient: KtorClient,
) : RickAndMortyRepository {

    override suspend fun fetchCharacters(page: Int): Resource<Characters> {
        return ktorClient.getCharacters(page)
    }

    override suspend fun fetchCharacter(characterId: Int): Resource<Character> {
        return ktorClient.getCharacter(characterId)
    }

    override suspend fun fetchEpisodes(episodeIds: List<Int>): Resource<List<Episode>> {
        return ktorClient.getEpisodes(episodeIds)
    }
}