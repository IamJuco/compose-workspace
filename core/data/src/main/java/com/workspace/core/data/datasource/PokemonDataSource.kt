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
        return try {
            val response = service.getPokemonList(offset, limit)
            ServiceResult.Success(response)
        } catch (e: Exception) {
            ServiceResult.Error(mapToErrorCode(e))
        }
    }

    override suspend fun fetchPokemonDetail(pokemonId: Int): ServiceResult<PokemonResponse> {
        return try {
            val response = service.getPokemon(pokemonId)
            ServiceResult.Success(response)
        } catch (e: Exception) {
            ServiceResult.Error(mapToErrorCode(e))
        }
    }
}

//    private fun mapToErrorCode(exception: Exception): ErrorCode {
//        return when (exception) {
//            is java.net.SocketTimeoutException -> ErrorCode.NETWORK_ERROR
//            is java.io.IOException -> ErrorCode.NETWORK_ERROR
//            is retrofit2.HttpException -> ErrorCode.API_ERROR
//            else -> ErrorCode.NOT_FOUND
//        }
//    }
