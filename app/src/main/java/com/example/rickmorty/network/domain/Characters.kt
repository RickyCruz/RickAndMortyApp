package com.example.rickmorty.network.domain

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