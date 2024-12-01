package com.workspace.core.data.mapper

import com.workspace.core.data.dto.PokemonResponse
import com.workspace.core.data.dto.PokemonResult
import com.workspace.core.domain.model.Pokemon
import com.workspace.core.domain.model.PokemonList

fun PokemonResponse.toDomainModel(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        height = "${height * 10} cm", // decimetres → centimetres
        weight = "${weight / 10.0} kg", // hectograms → kilograms
        imageUrl = sprites.frontDefault ?: ""
    )
}

fun PokemonResult.toDomainModel(): PokemonList {
    val id = url.split("/").dropLast(1).last().toInt() // URL에서 ID 추출
    return PokemonList(
        name = name,
        id = id,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    )
}