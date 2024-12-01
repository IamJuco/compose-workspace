package com.workspace.core.data.dto

data class PokemonListResponse(
    val count: Int, // 전체 포켓몬 개수
    val next: String?, // 다음 페이지 URL
    val previous: String?, // 이전 페이지 URL
    val results: List<PokemonResult> // 포켓몬 결과 리스트
)

data class PokemonResult(
    val name: String, // 포켓몬 이름
    val url: String   // 포켓몬 상세 URL
)