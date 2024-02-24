package com.example.rickmorty.common

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Failure<T>(val exception: Exception) : Resource<T>

    fun <R> mapSuccess(transform: (T) -> R): Resource<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> Failure(exception)
        }
    }

    fun onSuccess(block: (T) -> Unit): Resource<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit): Resource<T> {
        if (this is Failure) block(exception)
        return this
    }
}