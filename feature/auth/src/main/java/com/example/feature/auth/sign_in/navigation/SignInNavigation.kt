package com.example.feature.auth.sign_in.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.auth.sign_in.SignInRoute
import com.workspace.core.domain.navigation.RouteModel

fun NavGraphBuilder.signInRoute() {
    composable(RouteModel.SignIn.ROUTE) {
        SignInRoute()
    }
}