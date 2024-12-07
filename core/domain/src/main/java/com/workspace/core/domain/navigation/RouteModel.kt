package com.workspace.core.domain.navigation

import kotlinx.serialization.Serializable

sealed interface RouteModel {
    @Serializable
    data object Home : RouteModel {
        const val ROUTE = "home"
    }

    @Serializable
    data object MyPage : RouteModel {
        const val ROUTE = "mypage"
    }

    @Serializable
    data class Detail(val pokemonId: Int) : RouteModel {
        companion object {
            const val ROUTE = "detail"
            const val ARG_POKEMON_ID = "pokemonId"
        }

        val routeWithArgs: String
            get() = "$ROUTE/{$ARG_POKEMON_ID}"
    }
}