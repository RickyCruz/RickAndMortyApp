package com.example.rickmorty.components.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.rickmorty.network.domain.CharacterStatus
import com.example.rickmorty.ui.theme.Action

@Composable
fun CharacterDetailsNameComponent(name: String, status: CharacterStatus) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = name,
            fontSize = 42.sp,
            lineHeight = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Action
        )

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