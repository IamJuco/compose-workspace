package com.example.feature.signup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.feature.signup.screen.EmailVerificationRoute
import com.workspace.core.domain.navigation.RouteModel

fun NavController.navigateToEmailVerification(navOptions: NavOptions) {
    navigate(RouteModel.EmailVerification, navOptions)
}

fun NavGraphBuilder.emailVerificationNavGraph(
    padding: PaddingValues,
    navigateToPasswordCheck: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    popBackStack: () -> Unit
) {
    composable<RouteModel.EmailVerification> {
        EmailVerificationRoute(
            padding = padding,
            navigateToPasswordCheck = navigateToPasswordCheck,
            onShowSnackBar = onShowSnackBar,
            popBackStack = popBackStack
        )
    }
}