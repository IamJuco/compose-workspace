package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.Pokemon
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemonId: Int): ServiceResult<Pokemon> {
        return repository.getPokemonDetail(pokemonId)
    }
}