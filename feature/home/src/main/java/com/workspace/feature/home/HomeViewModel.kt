package com.workspace.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.workspace.core.domain.model.PokemonList
import com.workspace.core.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _appliedQuery = MutableStateFlow("")
    val appliedQuery: StateFlow<String> = _appliedQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pokemonPagingData: Flow<PagingData<PokemonList>> =
        appliedQuery
            .debounce(300L)
            .distinctUntilChanged()
            .flatMapLatest { query ->
            getPokemonListUseCase().map { pagingData ->
                if (query.isEmpty()) {
                    pagingData
                } else {
                    pagingData.filter { it.name.contains(query, ignoreCase = true) }
                }
            }
        }.cachedIn(viewModelScope)

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    fun applySearchQuery() {
        _appliedQuery.update { _searchQuery.value }
    }
}