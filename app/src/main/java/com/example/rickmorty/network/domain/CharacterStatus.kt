package com.example.rickmorty.network.domain

import androidx.compose.ui.graphics.Color
import com.example.rickmorty.ui.theme.md_theme_light_tertiary
import com.example.rickmorty.ui.theme.md_theme_light_error
import com.example.rickmorty.ui.theme.md_theme_light_inversePrimary

sealed class CharacterStatus(val displayName: String, val color: Color) {
    data object Alive : CharacterStatus(displayName = "Alive", color = md_theme_light_tertiary)
    data object Dead : CharacterStatus(displayName = "Dead", color = md_theme_light_error)
    data object Unknown : CharacterStatus(displayName = "Unknown", color = md_theme_light_inversePrimary)
}