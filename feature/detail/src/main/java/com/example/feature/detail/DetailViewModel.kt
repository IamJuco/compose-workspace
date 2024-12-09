package com.example.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workspace.core.domain.model.Pokemon
import com.workspace.core.domain.model.ServiceResult
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

    private val _pokemonDetail = MutableStateFlow<ServiceResult<Pokemon>>(ServiceResult.Loading)
    val pokemonDetail: StateFlow<ServiceResult<Pokemon>> = _pokemonDetail

    fun loadPokemonDetail(pokemonId: Int) {
        viewModelScope.launch {
            _pokemonDetail.update { getPokemonDetailUseCase(pokemonId) }
        }
    }
}