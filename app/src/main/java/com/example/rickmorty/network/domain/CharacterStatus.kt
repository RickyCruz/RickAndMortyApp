package com.example.rickmorty.network.domain

import androidx.compose.ui.graphics.Color

sealed class CharacterStatus(val displayName: String, val color: Color) {
    data object Alive : CharacterStatus(displayName = "Alive", color = Color.Green)
    data object Dead : CharacterStatus(displayName = "Dead", color = Color.Red)
    data object Unknown : CharacterStatus(displayName = "Unknown", color = Color.Yellow)
}