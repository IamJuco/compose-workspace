package com.workspace.core.data.mapper

import com.workspace.core.data.dto.PokemonResponse
import com.workspace.core.domain.model.Pokemon

fun PokemonResponse.toDomainModel(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        height = "${height * 10} cm", // decimetres → centimetres
        weight = "${weight / 10.0} kg", // hectograms → kilograms
        imageUrl = sprites.frontDefault ?: ""
    )
}