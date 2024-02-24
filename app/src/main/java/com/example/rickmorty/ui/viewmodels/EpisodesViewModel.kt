package com.example.rickmorty.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.R
import com.example.rickmorty.ui.screens.CharacterEpisodesViewState
import com.example.rickmorty.di.StringResourcesProvider
import com.example.rickmorty.domain.usecase.GetEpisodesByIdUseCase
import com.example.rickmorty.domain.usecase.GetCharacterByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val stringResources: StringResourcesProvider,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val getEpisodesByIdUseCase: GetEpisodesByIdUseCase,
) : ViewModel() {
    private val _stateFlow = MutableStateFlow<CharacterEpisodesViewState>(
        value = CharacterEpisodesViewState.Loading
    )
    val stateFlow = _stateFlow.asStateFlow()

    fun fetchEpisodes(characterId: Int) = viewModelScope.launch {
        _stateFlow.update { return@update CharacterEpisodesViewState.Loading }
        getCharacterByIdUseCase(characterId).onSuccess { character ->
            _stateFlow.update {
                return@update CharacterEpisodesViewState.Success(character = character)
            }
            launch {
                getEpisodesByIdUseCase(character.episodeIds, characterId).onSuccess { episodes ->
                    _stateFlow.update {
                        return@update CharacterEpisodesViewState.Success(
                            character = character,
                            episodes = episodes
                        )
                    }
                }.onFailure { exception ->
                    _stateFlow.update {
                        return@update CharacterEpisodesViewState.Error(
                            message = exception.message ?: stringResources.getString(R.string.unknown_error),
                        )
                    }
                }
            }
        }.onFailure { exception ->
            _stateFlow.update {
                return@update CharacterEpisodesViewState.Error(
                    message = exception.message ?: stringResources.getString(R.string.unknown_error),
                )
            }
        }
    }
}