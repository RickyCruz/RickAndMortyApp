package com.example.rickmorty.components.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rickmorty.components.shared.CharacterNameComponent
import com.example.rickmorty.network.domain.CharacterStatus

@Composable
fun CharacterDetailsNameComponent(name: String, status: CharacterStatus) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CharacterNameComponent(name = name)
        CharacterStatusComponent(characterStatus = status)
    }
}

@Preview
@Composable
fun NamePreviewAlive() {
    CharacterDetailsNameComponent(name = "Morty Smith", status = CharacterStatus.Alive)
}

@Preview
@Composable
fun NamePreviewDead() {
    CharacterDetailsNameComponent(name = "Morty Smith", status = CharacterStatus.Dead)
}

@Preview
@Composable
fun NamePreviewUnknown() {
    CharacterDetailsNameComponent(name = "Morty Smith", status = CharacterStatus.Unknown)
}