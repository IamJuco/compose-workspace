package com.workspace.core.domain.navigation

import kotlinx.serialization.Serializable

sealed interface RouteModel {
    @Serializable
    data object Home : RouteModel {
        const val ROUTE = "home"
    }

    @Serializable
    data object MyPage : RouteModel {
        const val ROUTE = "my_page"
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

    @Serializable
    data object SignIn : RouteModel {
        const val ROUTE = "sign_in"
    }


    @Serializable
    data object SignUp : RouteModel {
        const val ROUTE = "sign_up"
    }

}