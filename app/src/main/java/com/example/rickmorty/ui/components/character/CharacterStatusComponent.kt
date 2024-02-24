package com.example.rickmorty.ui.components.character

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickmorty.domain.model.CharacterStatus
import com.example.rickmorty.ui.theme.RickMortyTheme

@Composable
fun CharacterStatusComponent(characterStatus: CharacterStatus) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(width = 1.dp, color = characterStatus.color, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = characterStatus.displayName,
            color = characterStatus.color,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreviewAlive() {
    RickMortyTheme {
        CharacterStatusComponent(characterStatus = CharacterStatus.Alive)
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreviewDead() {
    RickMortyTheme {
        CharacterStatusComponent(characterStatus = CharacterStatus.Dead)
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreviewUnknown() {
    RickMortyTheme {
        CharacterStatusComponent(characterStatus = CharacterStatus.Unknown)
    }
}