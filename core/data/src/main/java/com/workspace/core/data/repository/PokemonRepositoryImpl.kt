package com.workspace.core.data.repository

import com.workspace.core.data.api.PokeService
import com.workspace.core.data.mapper.toDomainModel
import com.workspace.core.domain.model.Pokemon
import com.workspace.core.domain.model.PokemonList
import com.workspace.core.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokeService: PokeService
) : PokemonRepository {
    override suspend fun getPokemon(id: Int): Pokemon {
        val response = pokeService.getPokemon(id)
        return response.toDomainModel()
    }

    override suspend fun getPokemonList(offset: Int, limit: Int): List<PokemonList> {
        val response = pokeService.getPokemonList(offset, limit)
        return response.results.map { it.toDomainModel() }
    }

}