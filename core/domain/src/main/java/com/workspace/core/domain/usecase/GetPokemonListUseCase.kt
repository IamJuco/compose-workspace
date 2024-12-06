package com.workspace.core.domain.usecase

import androidx.paging.PagingData
import com.workspace.core.domain.model.PokemonList
import com.workspace.core.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<PagingData<PokemonList>> {
        return repository.getPokemonPagingData()
    }
}