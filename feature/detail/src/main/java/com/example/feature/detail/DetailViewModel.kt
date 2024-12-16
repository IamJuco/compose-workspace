package com.example.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workspace.core.domain.model.Pokemon
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.UiState
import com.workspace.core.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _pokemonDetailState = MutableStateFlow<UiState<Pokemon>>(UiState.Idle)
    val pokemonDetailState: StateFlow<UiState<Pokemon>> = _pokemonDetailState

    fun loadPokemonDetail(pokemonId: Int) {
        viewModelScope.launch {
            _pokemonDetailState.value = UiState.Loading
            when (val result = getPokemonDetailUseCase(pokemonId)) {
                is ServiceResult.Success -> _pokemonDetailState.value = UiState.Success(result.data)
                is ServiceResult.Error -> _pokemonDetailState.value = UiState.Error(
                    result.errorCode,
                    result.errorMessage
                )
            }
        }
    }
}