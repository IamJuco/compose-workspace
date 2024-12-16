package com.example.feature.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.feature.login.LoginRoute
import com.workspace.core.domain.navigation.RouteModel

fun NavController.navigateLogin(navOptions: NavOptions) {
    navigate(RouteModel.Login, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit
) {
    composable<RouteModel.Login> {
        LoginRoute(
            padding = padding,
            onShowSnackBar = onShowSnackBar
        )
    }
}