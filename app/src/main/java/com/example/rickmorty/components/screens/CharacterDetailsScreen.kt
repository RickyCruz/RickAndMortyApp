package com.example.rickmorty.components.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickmorty.R
import com.example.rickmorty.components.character.CharacterDetailsNameComponent
import com.example.rickmorty.components.character.CharacterImageComponent
import com.example.rickmorty.components.shared.CharacterDataComponent
import com.example.rickmorty.components.shared.DataCharacter
import com.example.rickmorty.components.shared.LoadingState
import com.example.rickmorty.network.domain.Character
import com.example.rickmorty.viewmodels.CharacterDetailsViewModel
import kotlinx.coroutines.launch

sealed interface CharacterDetailsViewState {
    data object Loading : CharacterDetailsViewState
    data class Error(val message: String) : CharacterDetailsViewState
    data class Success(val character: Character, val characterData: List<DataCharacter>) :
        CharacterDetailsViewState
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun CharacterDetailsScreen(
    characterId: Int,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onEpisodeClicked: (Int) -> Unit,
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchCharacter(characterId)
    })

    val scope = rememberCoroutineScope()
    val snackHostState = remember { SnackbarHostState() }
    val state by viewModel.stateFlow.collectAsState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackHostState)
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 16.dp)
        ) {
            when (val viewState = state) {
                is CharacterDetailsViewState.Loading -> item { LoadingState() }

                is CharacterDetailsViewState.Error -> {
                    scope.launch {
                        snackHostState.showSnackbar(
                            message = viewState.message,
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                }

                is CharacterDetailsViewState.Success -> {
                    item {
                        CharacterDetailsNameComponent(
                            name = viewState.character.name,
                            status = viewState.character.status
                        )
                    }

                    item { Spacer(modifier = Modifier.height(12.dp)) }

                    item {
                        CharacterImageComponent(imageUrl = viewState.character.imageUrl)
                    }

                    items(viewState.characterData) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CharacterDataComponent(data = it)
                    }

                    item { Spacer(modifier = Modifier.height(24.dp)) }

                    item {
                        Text(
                            text = stringResource(R.string.screen_character_detail_action_view_all),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    onEpisodeClicked(characterId)
                                }
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                        )
                    }

                    item { Spacer(modifier = Modifier.height(64.dp)) }
                }
            }
        }
    }
}