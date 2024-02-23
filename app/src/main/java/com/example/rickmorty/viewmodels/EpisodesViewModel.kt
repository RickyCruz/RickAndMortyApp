package com.example.rickmorty.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.components.screens.CharacterEpisodesViewState
import com.example.rickmorty.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _stateFlow = MutableStateFlow<CharacterEpisodesViewState>(
        value = CharacterEpisodesViewState.Loading
    )
    val stateFlow = _stateFlow.asStateFlow()

    fun fetchEpisodes(characterId: Int) = viewModelScope.launch {
        _stateFlow.update { return@update CharacterEpisodesViewState.Loading }
        characterRepository.fetchCharacter(characterId).onSuccess { character ->
            _stateFlow.update {
                return@update CharacterEpisodesViewState.Success(character = character)
            }
            launch {
                characterRepository.fetchEpisodes(character.episodeIds).onSuccess { episodes ->
                    _stateFlow.update {
                        return@update CharacterEpisodesViewState.Success(
                            character = character,
                            episodes = episodes
                        )
                    }
                }.onFailure { exception ->
                    _stateFlow.update {
                        return@update CharacterEpisodesViewState.Error(
                            message = exception.message ?: "Unknown error occurred"
                        )
                    }
                }
            }
        }.onFailure { exception ->
            _stateFlow.update {
                return@update CharacterEpisodesViewState.Error(
                    message = exception.message ?: "Unknown error occurred"
                )
            }
        }
    }
}