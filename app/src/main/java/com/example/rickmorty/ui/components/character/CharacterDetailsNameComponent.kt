package com.example.rickmorty.ui.components.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickmorty.ui.components.shared.CharacterNameComponent
import com.example.rickmorty.domain.model.CharacterStatus

@Composable
fun CharacterDetailsNameComponent(name: String, status: CharacterStatus) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CharacterNameComponent(name = name)
        Spacer(modifier = Modifier.width(6.dp))
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