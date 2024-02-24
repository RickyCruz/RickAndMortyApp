package com.example.rickmorty.components.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

data class DataCharacter(
    val title: String,
    val description: String
)

@Composable
fun CharacterDataComponent(data: DataCharacter) {
    Column {
        Text(
            text = data.title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = data.description,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Preview
@Composable
fun CharacterDataComponentPreview() {
    val data = DataCharacter(title = "Last known location", description = "Citadel of Ricks")
    CharacterDataComponent(data = data)
}