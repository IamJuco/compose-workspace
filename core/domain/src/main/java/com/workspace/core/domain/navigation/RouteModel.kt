package com.workspace.core.domain.navigation

import kotlinx.serialization.Serializable

sealed interface RouteModel {
    @Serializable
    data object Login : RouteModel

    @Serializable
    data object SignUp : RouteModel

    @Serializable
    data class Detail(val pokemonId: Int) : MainMenuRoute
}

sealed interface MainMenuRoute : RouteModel {
    @Serializable
    data object Home : MainMenuRoute

    @Serializable
    data object MyPage : MainMenuRoute
}