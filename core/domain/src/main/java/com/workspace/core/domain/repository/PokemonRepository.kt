package com.workspace.core.domain.repository

import com.workspace.core.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemon(id: Int): Pokemon
}