package com.workspace.core.data.api

import com.workspace.core.data.dto.PokemonListResponse
import com.workspace.core.data.dto.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {
    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: Int
    ): PokemonResponse

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponse
}