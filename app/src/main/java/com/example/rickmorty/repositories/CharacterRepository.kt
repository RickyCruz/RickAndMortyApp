package com.example.rickmorty.repositories

import com.example.rickmorty.network.ApiOperation
import com.example.rickmorty.network.KtorClient
import com.example.rickmorty.network.domain.Character
import com.example.rickmorty.network.domain.Episode
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val ktorClient: KtorClient,
) {
    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character> {
        return ktorClient.getCharacter(characterId)
    }

    suspend fun fetchEpisodes(episodeIds: List<Int>): ApiOperation<List<Episode>> {
        return ktorClient.getEpisodes(episodeIds)
    }
}