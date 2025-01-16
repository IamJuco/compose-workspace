package com.example.feature.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.feature.detail.DetailRoute
import com.workspace.core.domain.navigation.RouteModel

fun NavController.navigateToDetail(pokemonId: Int, navOptions: NavOptions? = null) {
    navigate(RouteModel.Detail(pokemonId), navOptions)
}

fun NavGraphBuilder.detailNavGraph(
    popBackStack: () -> Unit,
    padding: PaddingValues,
) {
    composable<RouteModel.Detail> { navBackStackEntry ->
        val pokemonId = navBackStackEntry.toRoute<RouteModel.Detail>().pokemonId
        DetailRoute(
            pokemonId = pokemonId,
            popBackStack = popBackStack,
            padding = padding,
        )
    }
}