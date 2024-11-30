package com.workspace.core.data.dto

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("id") val id: Int, // 포켓몬 id
    @SerializedName("name") val name: String, // 포켓몬 이름
    @SerializedName("height") val height: Int, // 포켓몬 키
    @SerializedName("weight") val weight: Int, // 포켓몬 몸무기
    @SerializedName("sprites") val sprites: Sprites
)

data class Sprites(
    @SerializedName("front_default") val frontDefault: String?, // 포켓몬 기본 이미지 (정면)
)