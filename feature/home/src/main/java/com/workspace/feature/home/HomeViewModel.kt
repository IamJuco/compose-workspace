package com.workspace.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workspace.core.domain.model.PokemonList
import com.workspace.core.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {
    private val _pokemonList = MutableStateFlow<List<PokemonList>>(emptyList())
    val pokemonList: StateFlow<List<PokemonList>> = _pokemonList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentOffset = 0
    private val pageSize = 20

    init {
        fetchPokemonList()
    }

    fun fetchPokemonList() {
        if (_isLoading.value) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newPokemons = getPokemonListUseCase(currentOffset, pageSize)
                _pokemonList.value += newPokemons
                currentOffset += pageSize
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "error"
            } finally {
                _isLoading.value = false
            }
        }
    }
}