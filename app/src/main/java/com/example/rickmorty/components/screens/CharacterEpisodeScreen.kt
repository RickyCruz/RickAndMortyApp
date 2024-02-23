package com.example.rickmorty.components.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickmorty.components.character.CharacterImageComponent
import com.example.rickmorty.components.episode.EpisodeRowComponent
import com.example.rickmorty.components.shared.CharacterNameComponent
import com.example.rickmorty.components.shared.LoadingState
import com.example.rickmorty.network.domain.Character
import com.example.rickmorty.network.domain.Episode
import com.example.rickmorty.ui.theme.Primary
import com.example.rickmorty.ui.theme.TextPrimary
import com.example.rickmorty.viewmodels.EpisodesViewModel

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
    ) {
        when (val viewState = state) {
            is CharacterEpisodesViewState.Loading -> LoadingState()

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreen(character: Character, episodes: List<Episode>) {
    LazyColumn(contentPadding = PaddingValues(all = 16.dp)) {
        item { CharacterNameComponent(name = character.name) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { CharacterImageComponent(imageUrl = character.imageUrl) }
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Primary)
            .padding(top = 8.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Season $seasonNumber",
            color = TextPrimary,
            fontSize = 32.sp,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = TextPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 4.dp)
        )
    }
}