package com.example.feature.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.feature.detail.DetailRoute
import com.workspace.core.domain.navigation.RouteModel

fun NavGraphBuilder.detailRoute() {
    composable(
        route = "${RouteModel.Detail.ROUTE}/{${RouteModel.Detail.ARG_POKEMON_ID}}",
        arguments = listOf(navArgument(RouteModel.Detail.ARG_POKEMON_ID) { type = NavType.IntType })
    ) { backStackEntry ->
        val pokemonId = backStackEntry.arguments?.getInt(RouteModel.Detail.ARG_POKEMON_ID) ?: 0
        DetailRoute(pokemonId = pokemonId)
    }
}