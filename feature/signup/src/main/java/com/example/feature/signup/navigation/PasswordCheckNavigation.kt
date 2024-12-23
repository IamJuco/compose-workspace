package com.example.feature.signup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.feature.signup.screen.PasswordCheckRoute
import com.workspace.core.domain.navigation.RouteModel

fun NavController.navigateToPasswordCheck(navOptions: NavOptions) {
    navigate(RouteModel.PasswordCheck, navOptions)
}

fun NavGraphBuilder.passwordCheckNavGraph(
    padding: PaddingValues,
    navigateToLogin: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    composable<RouteModel.PasswordCheck> {
        PasswordCheckRoute(
            padding = padding,
            navigateToLogin = navigateToLogin,
            onShowSnackBar = onShowSnackBar
        )
    }
}