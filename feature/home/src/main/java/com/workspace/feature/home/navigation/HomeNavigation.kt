package com.workspace.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.workspace.core.domain.navigation.MainMenuRoute
import com.workspace.feature.home.HomeRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(MainMenuRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    navigateToDetail: (Int) -> Unit,
    padding: PaddingValues,
    onShowLoginSnackBar: () -> Unit,
    hasToken: Boolean
) {
    composable<MainMenuRoute.Home> {
        HomeRoute(
            navigateToDetail = navigateToDetail,
            padding = padding
        )
    }
}