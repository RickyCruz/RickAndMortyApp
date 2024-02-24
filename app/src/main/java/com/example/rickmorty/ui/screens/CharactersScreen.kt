package com.example.rickmorty.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickmorty.R
import com.example.rickmorty.ui.components.character.CharacterItemComponent
import com.example.rickmorty.ui.components.shared.FullScreenLoading
import com.example.rickmorty.domain.model.Character
import com.example.rickmorty.ui.viewmodels.CharactersViewModel

sealed interface CharactersViewState {
    data object Loading : CharactersViewState
    data class Error(val message: String) : CharactersViewState
    data class Success(
        val characters: List<Character> = emptyList(),
        val showPrevious: Boolean = false,
        val showNext: Boolean = false
    ) : CharactersViewState
}

@Composable
fun CharactersScreen(
    onItemClick: (Int) -> Unit,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchCharacters(increase = false)
    })

    val snackHostState = remember { SnackbarHostState() }
    val state by viewModel.stateFlow.collectAsState()
    var characters = emptyList<Character>()
    var showPrevious = false
    var showNext = false
    var isLoading = false

    when (val viewState = state) {
        is CharactersViewState.Success -> {
            characters = viewState.characters
            showPrevious = viewState.showPrevious
            showNext = viewState.showNext
            isLoading = false
        }

        is CharactersViewState.Loading -> isLoading = true

        is CharactersViewState.Error -> {
            LaunchedEffect(key1 = Unit, block = {
                snackHostState.showSnackbar(
                    message = viewState.message,
                    duration = SnackbarDuration.Indefinite
                )
            })
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackHostState)
        },
        topBar = {
            CharactersTopBar()
        },
        bottomBar = {
            CharactersBottomBar(
                showPrevious = showPrevious,
                showNext = showNext,
                onPreviousPress = { viewModel.fetchCharacters(increase = false) },
                onNextPress = { viewModel.fetchCharacters(increase = true) }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
        ) {
            LazyColumn(contentPadding = PaddingValues(vertical = 6.dp),
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(characters.size) { index ->
                        CharacterItemComponent(
                            modifier = Modifier.fillMaxWidth(),
                            item = characters[index],
                            onItemClick = { onItemClick(it) }
                        )
                    }
                }
            )

            if (isLoading) FullScreenLoading()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.screen_characters_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
    )
}

@Composable
fun CharactersBottomBar(
    showPrevious: Boolean,
    showNext: Boolean,
    onPreviousPress: () -> Unit,
    onNextPress: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = onPreviousPress,
                enabled = showPrevious,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text(text = stringResource(R.string.screen_characters_action_prev))
            }
            TextButton(
                onClick = onNextPress,
                enabled = showNext,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text(text = stringResource(R.string.screen_characters_action_next))
            }
        }
    }
}