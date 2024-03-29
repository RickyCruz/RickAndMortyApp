package com.example.rickmorty.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.R
import com.example.rickmorty.di.StringResourcesProvider
import com.example.rickmorty.domain.usecase.GetCharactersByPageUseCase
import com.example.rickmorty.ui.screens.CharactersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val stringResources: StringResourcesProvider,
    private val getCharactersByPageUseCase: GetCharactersByPageUseCase,
) : ViewModel() {
    private val _stateFlow = MutableStateFlow<CharactersViewState>(
        value = CharactersViewState.Loading
    )
    val stateFlow = _stateFlow.asStateFlow()
    private var currentPage = 1

    fun fetchCharacters(increase: Boolean = false) = viewModelScope.launch {
        _stateFlow.update { return@update CharactersViewState.Loading }
        if (increase) currentPage++ else if (currentPage > 1) currentPage--
        val showPrevious = currentPage > 1
        val showNext = currentPage < 42

        getCharactersByPageUseCase(currentPage).onSuccess { characters ->
            _stateFlow.update {
                return@update CharactersViewState.Success(
                    characters = characters.results,
                    showPrevious = showPrevious,
                    showNext = showNext
                )
            }
        }.onFailure { exception ->
            _stateFlow.update {
                return@update CharactersViewState.Error(
                    message = exception.message ?: stringResources.getString(R.string.unknown_error),
                )
            }
        }
    }
}