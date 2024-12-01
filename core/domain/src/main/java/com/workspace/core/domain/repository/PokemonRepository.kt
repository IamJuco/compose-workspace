package com.workspace.core.domain.repository

import com.workspace.core.domain.model.PokemonList
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData

interface PokemonRepository {
    fun getPokemonPagingData(): Flow<PagingData<PokemonList>>
}