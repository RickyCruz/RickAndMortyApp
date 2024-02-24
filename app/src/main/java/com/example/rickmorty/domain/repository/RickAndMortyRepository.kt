package com.example.rickmorty.domain.repository

import com.example.rickmorty.common.Resource
import com.example.rickmorty.domain.model.Character
import com.example.rickmorty.domain.model.Characters
import com.example.rickmorty.domain.model.Episode

interface RickAndMortyRepository {
    suspend fun fetchCharacters(page: Int): Resource<Characters>
    suspend fun fetchCharacter(characterId: Int): Resource<Character>
    suspend fun fetchEpisodes(episodeIds: List<Int>, characterId: Int): Resource<List<Episode>>
}