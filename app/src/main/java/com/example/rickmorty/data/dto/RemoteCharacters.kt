package com.example.rickmorty.data.dto


import com.example.rickmorty.domain.model.Characters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCharacters(
    @SerialName("info")
    val info: Info,
    @SerialName("results")
    val results: List<RemoteCharacter>
) {
    @Serializable
    data class Info(
        @SerialName("count")
        val count: Int,
        @SerialName("next")
        val next: String,
        @SerialName("pages")
        val pages: Int,
        @SerialName("prev")
        val prev: String? = null
    )
}

fun RemoteCharacters.toDomainCharacters(): Characters {
    return Characters(
        info = Characters.Info(
            count = info.count,
            next = info.next,
            pages = info.pages,
            prev = info.prev,
        ),
        results = results.map {
            it.toDomainCharacter()
        }
    )
}