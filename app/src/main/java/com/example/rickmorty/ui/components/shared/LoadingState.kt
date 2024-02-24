package com.example.rickmorty.ui.components.shared

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val defaultModifier = Modifier
    .fillMaxSize()
    .padding(all = 128.dp)

@Composable
fun LoadingState(@SuppressLint("ModifierParameter") modifier: Modifier = defaultModifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}