package com.workspace.core.data.datasource

import com.workspace.core.data.api.PokeService
import com.workspace.core.data.dto.PokemonListResponse

interface PokemonDataSource {
    suspend fun fetchPokemonList(offset: Int, limit: Int): PokemonListResponse
}

class PokemonDataSourceImpl(
    private val service: PokeService
) : PokemonDataSource {
    override suspend fun fetchPokemonList(offset: Int, limit: Int): PokemonListResponse {
        return safeApiCall { service.getPokemonList(offset, limit) }
    }
}