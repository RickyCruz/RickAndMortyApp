package com.example.rickmorty.components.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.rickmorty.ui.theme.Action
import com.example.rickmorty.ui.theme.TextPrimary

data class DataCharacter(
    val title: String,
    val description: String
)

@Composable
fun CharacterDataComponent(data: DataCharacter) {
    Column {
        Text(
            text = data.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Action
        )
        Text(
            text = data.description,
            fontSize = 24.sp,
            color = TextPrimary
        )
    }
}

@Preview
@Composable
fun CharacterDataComponentPreview() {
    val data = DataCharacter(title = "Last known location", description = "Citadel of Ricks")
    CharacterDataComponent(data = data)
}