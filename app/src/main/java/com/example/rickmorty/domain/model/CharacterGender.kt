package com.example.rickmorty.domain.model

sealed class CharacterGender(val displayName: String) {
    data object Male : CharacterGender(displayName = "Male")
    data object Female : CharacterGender(displayName = "Female")
    data object Genderless : CharacterGender(displayName = "No gender")
    data object Unknown : CharacterGender(displayName = "Not specified")
}