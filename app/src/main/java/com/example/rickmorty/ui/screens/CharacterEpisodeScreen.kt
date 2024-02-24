package com.example.rickmorty.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickmorty.R
import com.example.rickmorty.ui.components.character.CharacterImageComponent
import com.example.rickmorty.ui.components.episode.EpisodeRowComponent
import com.example.rickmorty.ui.components.shared.CharacterNameComponent
import com.example.rickmorty.ui.components.shared.FullScreenLoading
import com.example.rickmorty.domain.model.Character
import com.example.rickmorty.domain.model.Episode
import com.example.rickmorty.ui.viewmodels.EpisodesViewModel

sealed interface CharacterEpisodesViewState {
    data object Loading : CharacterEpisodesViewState
    data class Error(val message: String) : CharacterEpisodesViewState
    data class Success(val character: Character, val episodes: List<Episode> = emptyList()) :
        CharacterEpisodesViewState
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun CharacterEpisodeScreen(
    characterId: Int,
    viewModel: EpisodesViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchEpisodes(characterId)
    })

    val snackHostState = remember { SnackbarHostState() }
    val state by viewModel.stateFlow.collectAsState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackHostState)
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
        ) {
            when (val viewState = state) {
                is CharacterEpisodesViewState.Loading -> FullScreenLoading()

                is CharacterEpisodesViewState.Error -> {
                    LaunchedEffect(key1 = Unit, block = {
                        snackHostState.showSnackbar(
                            message = viewState.message,
                            duration = SnackbarDuration.Indefinite
                        )
                    })
                }

                is CharacterEpisodesViewState.Success -> {
                    MainScreen(character = viewState.character, episodes = viewState.episodes)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreen(character: Character, episodes: List<Episode>) {
    LazyColumn(
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { CharacterNameComponent(name = character.name) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            CharacterImageComponent(
                imageUrl = character.imageUrl,
                modifier = Modifier
                    .size(128.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }

        episodes.groupBy { it.seasonNumber }.forEach { mapEntry ->
            stickyHeader { SeasonHeader(seasonNumber = mapEntry.key) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(mapEntry.value) { episode ->
                EpisodeRowComponent(episode = episode)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SeasonHeader(seasonNumber: Int) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Row {
            Text(
                text = stringResource(R.string.screen_character_episode_header, seasonNumber),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}