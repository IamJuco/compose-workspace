package com.example.feature.signup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.feature.signup.SignUpRoute
import com.workspace.core.domain.navigation.RouteModel

fun NavController.navigateToSignUp(navOptions: NavOptions) {
    navigate(RouteModel.SignUp, navOptions)
}

fun NavGraphBuilder.signUpNavGraph(
    padding: PaddingValues,
    navigateToLogin: () -> Unit
) {
    composable<RouteModel.SignUp> {
        SignUpRoute(
            padding = padding,
            navigateToLogin = navigateToLogin,
        )
    }
}