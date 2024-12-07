package com.workspace.core.domain.repository

import com.workspace.core.domain.model.PokemonList
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData
import com.workspace.core.domain.model.Pokemon
import com.workspace.core.domain.model.ServiceResult

interface PokemonRepository {
    fun getPokemonPagingData(): Flow<PagingData<PokemonList>>
    suspend fun getPokemonDetail(pokemonId: Int): ServiceResult<Pokemon>
}