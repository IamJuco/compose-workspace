package com.workspace.core.domain.repository

import com.workspace.core.domain.model.Pokemon
import com.workspace.core.domain.model.PokemonList

interface PokemonRepository {
    suspend fun getPokemon(id: Int): Pokemon
    suspend fun getPokemonList(offset: Int, limit: Int): List<PokemonList>
}