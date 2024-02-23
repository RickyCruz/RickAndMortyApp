package com.example.rickmorty.components.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.rickmorty.components.character.CharacterDetailsNameComponent
import com.example.rickmorty.components.shared.DataCharacter
import com.example.rickmorty.components.shared.DataComponent
import com.example.rickmorty.network.KtorClient
import com.example.rickmorty.network.domain.Character
import com.example.rickmorty.ui.theme.Action

@Composable
fun CharacterDetailsScreen(
    ktorClient: KtorClient,
    characterId: Int
) {
    var character by remember {
        mutableStateOf<Character?>(null)
    }

    val characterData: List<DataCharacter> by remember {
        derivedStateOf {
            buildList {
                character?.let { character ->
                    add(DataCharacter(title = "Last known location", description = character.location.name))
                    add(DataCharacter(title = "Species", description = character.species))
                    add(DataCharacter(title = "Gender", description = character.gender.displayName))
                    character.type.takeIf { it.isNotEmpty() }?.let { type ->
                        add(DataCharacter(title = "Type", description = type))
                    }
                    add(DataCharacter(title = "Origin", description = character.origin.name))
                    add(DataCharacter(title = "Episode count", description = character.episodeUrls.size.toString()))
                }
            }
        }
    }

    var hasError by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        ktorClient.getCharacter(characterId).onSuccess {
            character = it
        }.onFailure {
            hasError = true
        }
    })

    if (hasError) {
        AlertDialog(
            onDismissRequest = { hasError = false },
            title = { Text("Whoops!") },
            text = { Text("Something were wrong") },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { hasError = false }) {
                    Text("Ok".uppercase())
                }
            },
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        if (character == null) {
            item { LoadingState() }
            return@LazyColumn
        }

        item {
            CharacterDetailsNameComponent(
                name = character?.name.orEmpty(),
                status = character!!.status
            )
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        item {
            SubcomposeAsyncImage(
                model = character?.imageUrl.orEmpty(),
                contentDescription = "Character image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                loading = { LoadingState() }
            )
        }

        items(characterData) {
            Spacer(modifier = Modifier.height(32.dp))
            DataComponent(data = it)
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }

        // Button
        item {
            Text(
                text = "View all episodes",
                color = Action,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .border(
                        width = 1.dp,
                        color = Action,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {}
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
        }

        item { Spacer(modifier = Modifier.height(64.dp)) }
    }
}

@Composable
private fun LoadingState() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 128.dp),
        color = Action
    )
}