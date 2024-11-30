package com.workspace.core.data.api

import com.workspace.core.data.dto.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {
    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: Int
    ): PokemonResponse
}