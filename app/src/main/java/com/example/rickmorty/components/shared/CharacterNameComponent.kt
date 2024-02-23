package com.example.rickmorty.components.shared

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.rickmorty.ui.theme.Action

@Composable
fun CharacterNameComponent(name: String) {
    Text(
        text = name,
        fontSize = 42.sp,
        lineHeight = 42.sp,
        fontWeight = FontWeight.Bold,
        color = Action
    )
}

@Preview
@Composable
private fun CharacterNameComponentPreview() {
    CharacterNameComponent(name = "Rick Sanchez")
}