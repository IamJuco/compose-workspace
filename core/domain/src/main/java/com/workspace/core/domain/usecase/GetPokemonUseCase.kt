package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.Pokemon
import com.workspace.core.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Pokemon {
        return repository.getPokemon(id)
    }
}