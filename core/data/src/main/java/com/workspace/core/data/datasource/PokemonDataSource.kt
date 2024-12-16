package com.workspace.core.data.datasource

import com.workspace.core.data.api.PokeService
import com.workspace.core.data.dto.PokemonListResponse
import com.workspace.core.data.dto.PokemonResponse
import com.workspace.core.data.mapper.mapToErrorCode
import com.workspace.core.domain.model.ServiceResult

interface PokemonDataSource {
    suspend fun fetchPokemonList(offset: Int, limit: Int): ServiceResult<PokemonListResponse>
    suspend fun fetchPokemonDetail(pokemonId: Int): ServiceResult<PokemonResponse>
}

class PokemonDataSourceImpl(
    private val service: PokeService
) : PokemonDataSource {
    override suspend fun fetchPokemonList(
        offset: Int,
        limit: Int
    ): ServiceResult<PokemonListResponse> {
        return safeApiCall { service.getPokemonList(offset, limit) }
    }

    override suspend fun fetchPokemonDetail(pokemonId: Int): ServiceResult<PokemonResponse> {
        return safeApiCall { service.getPokemon(pokemonId) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): ServiceResult<T> {
        return try {
            ServiceResult.Success(apiCall())
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }
}