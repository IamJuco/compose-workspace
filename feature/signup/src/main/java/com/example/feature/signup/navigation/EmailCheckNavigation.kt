package com.example.feature.signup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.feature.signup.screen.EmailCheckRoute
import com.workspace.core.domain.navigation.RouteModel

fun NavController.navigateToEmailCheck(navOptions: NavOptions) {
    navigate(RouteModel.EmailCheck, navOptions)
}

fun NavGraphBuilder.emailCheckNavGraph(
    padding: PaddingValues,
    navigateToEmailVerification: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    popBackStack: () -> Unit
) {
    composable<RouteModel.EmailCheck> {
        EmailCheckRoute(
            padding = padding,
            navigateToEmailVerification = navigateToEmailVerification,
            onShowSnackBar = onShowSnackBar,
            popBackStack = popBackStack
        )
    }
}