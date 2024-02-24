package com.example.rickmorty.common

import javax.inject.Inject

class CacheManager<T> @Inject constructor() {
    private val cache = HashMap<String, T>()

    fun put(key: String, value: T) {
        cache[key] = value
    }

    fun get(key: String): T? {
        return cache[key]
    }
}