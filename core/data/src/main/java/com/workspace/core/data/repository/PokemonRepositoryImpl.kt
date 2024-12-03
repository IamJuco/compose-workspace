package com.workspace.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.workspace.core.data.api.PokeService
import com.workspace.core.data.datasource.PokemonDataSource
import com.workspace.core.data.datasource.PokemonPagingSource
import com.workspace.core.data.dto.PokemonResult
import com.workspace.core.data.mapper.toDomainModel
import com.workspace.core.domain.model.PokemonList
import com.workspace.core.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDataSource: PokemonDataSource
) : PokemonRepository {
    override fun getPokemonPagingData(): Flow<PagingData<PokemonList>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource(pokemonDataSource) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }
}