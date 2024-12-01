package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.PokemonList
import com.workspace.core.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(offset: Int, limit: Int): List<PokemonList> {
        return repository.getPokemonList(offset, limit)
    }
}
