package com.workspace.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.workspace.core.domain.api_error_handle.ApiException
import com.workspace.core.domain.model.PokemonList
import com.workspace.core.domain.model.UiState
import com.workspace.core.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Flow<PagingData<PokemonList>>>>(UiState.Loading)
    val uiState: StateFlow<UiState<Flow<PagingData<PokemonList>>>> = _uiState.asStateFlow()

    fun fetchPokemonList() {
        viewModelScope.launch {
            val result = getPokemonListUseCase()
            result.onSuccess { pagingFlow ->
                _uiState.update { (UiState.Success(pagingFlow)) }
            }.onFailure { throwable ->
                val apiException = throwable as? ApiException ?: ApiException.Unknown
                _uiState.update { (UiState.Failure(apiException)) }
            }
        }
    }
}