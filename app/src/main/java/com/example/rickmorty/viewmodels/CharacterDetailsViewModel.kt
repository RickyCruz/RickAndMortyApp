package com.example.rickmorty.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.components.screens.CharacterDetailsViewState
import com.example.rickmorty.components.shared.DataCharacter
import com.example.rickmorty.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _stateFlow = MutableStateFlow<CharacterDetailsViewState>(
        value = CharacterDetailsViewState.Loading
    )
    val stateFlow = _stateFlow.asStateFlow()

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        _stateFlow.update { return@update CharacterDetailsViewState.Loading }
        characterRepository.fetchCharacter(characterId).onSuccess { character ->
            val data = buildList {
                add(DataCharacter(title = "Last known location", description = character.location.name))
                add(DataCharacter(title = "Species", description = character.species))
                add(DataCharacter(title = "Gender", description = character.gender.displayName))
                character.type.takeIf { it.isNotEmpty() }?.let { type ->
                    add(DataCharacter(title = "Type", description = type))
                }
                add(DataCharacter(title = "Origin", description = character.origin.name))
                add(DataCharacter(title = "Episode count", description = character.episodeIds.size.toString()))
            }
            _stateFlow.update {
                return@update CharacterDetailsViewState.Success(
                    character = character,
                    characterData = data
                )
            }
        }.onFailure { exception ->
            _stateFlow.update {
                return@update CharacterDetailsViewState.Error(
                    message = exception.message ?: "Unknown error occurred"
                )
            }
        }
    }
}