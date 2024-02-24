package com.example.rickmorty.data.repository

import com.example.rickmorty.common.CacheManager
import com.example.rickmorty.common.Resource
import com.example.rickmorty.data.remote.KtorClient
import com.example.rickmorty.domain.model.Character
import com.example.rickmorty.domain.model.Characters
import com.example.rickmorty.domain.model.Episode
import com.example.rickmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val ktorClient: KtorClient,
    private val cacheCharacters: CacheManager<Characters>,
    private val cacheCharacter: CacheManager<Character>,
    private val cacheEpisodes: CacheManager<List<Episode>>,
) : RickAndMortyRepository {

    override suspend fun fetchCharacters(page: Int): Resource<Characters> {
        cacheCharacters.get("characters-$page")?.let { return Resource.Success(it) }

        return ktorClient.getCharacters(page).also { resource ->
            resource.onSuccess { cacheCharacters.put("characters-$page", it) }
        }
    }

    override suspend fun fetchCharacter(characterId: Int): Resource<Character> {
        cacheCharacter.get("character-$characterId")?.let { return Resource.Success(it) }

        return ktorClient.getCharacter(characterId).also { resource ->
            resource.onSuccess { cacheCharacter.put("character-$characterId", it) }
        }
    }

    override suspend fun fetchEpisodes(episodeIds: List<Int>, characterId: Int): Resource<List<Episode>> {
        cacheEpisodes.get("episodes-$characterId")?.let { return Resource.Success(it) }

        return ktorClient.getEpisodes(episodeIds).also { resource ->
            resource.onSuccess { cacheEpisodes.put("episodes-$characterId", it) }
        }
    }
}