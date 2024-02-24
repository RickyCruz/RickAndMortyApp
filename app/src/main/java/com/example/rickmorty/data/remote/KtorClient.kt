package com.example.rickmorty.data.remote

import com.example.rickmorty.common.Resource
import com.example.rickmorty.domain.model.Character
import com.example.rickmorty.domain.model.Characters
import com.example.rickmorty.domain.model.Episode
import com.example.rickmorty.data.dto.RemoteCharacter
import com.example.rickmorty.data.dto.RemoteCharacters
import com.example.rickmorty.data.dto.RemoteEpisode
import com.example.rickmorty.data.dto.toDomainCharacter
import com.example.rickmorty.data.dto.toDomainCharacters
import com.example.rickmorty.data.dto.toDomainEpisode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {
    private val client = HttpClient(OkHttp) {
        defaultRequest {
            url("https://rickandmortyapi.com/api/")
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getCharacters(page: Int): Resource<Characters> {
        return safeApiCall {
            client.get("character?page=$page")
                .body<RemoteCharacters>()
                .toDomainCharacters()
        }
    }

    suspend fun getCharacter(id: Int): Resource<Character> {
        return safeApiCall {
            client.get("character/$id")
                .body<RemoteCharacter>()
                .toDomainCharacter()
        }
    }

    suspend fun getEpisodes(episodeIds: List<Int>): Resource<List<Episode>> {
        return if (episodeIds.size == 1) {
            getEpisode(episodeIds[0]).mapSuccess {
                listOf(it)
            }
        } else {
            val ids = episodeIds.joinToString(separator = ",")

            safeApiCall {
                client.get("episode/$ids")
                    .body<List<RemoteEpisode>>()
                    .map { it.toDomainEpisode() }
            }
        }
    }

    private suspend fun getEpisode(episodeId: Int): Resource<Episode> {
        return safeApiCall {
            client.get("episode/$episodeId")
                .body<RemoteEpisode>()
                .toDomainEpisode()
        }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): Resource<T> {
        return try {
            Resource.Success(data = apiCall())
        } catch (e: Exception) {
            Resource.Failure(exception = e)
        }
    }
}