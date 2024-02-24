package com.example.rickmorty.domain.model

data class Characters(
    val info: Info,
    val results: List<Character>,
) {
    data class Info(
        val count: Int,
        val next: String,
        val pages: Int,
        val prev: String? = null
    )
}