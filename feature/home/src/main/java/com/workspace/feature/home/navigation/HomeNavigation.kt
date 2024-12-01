package com.workspace.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.workspace.core.domain.navigation.RouteModel
import com.workspace.feature.home.HomeScreen

fun NavGraphBuilder.homeRoute(navController: NavHostController) {
    composable(RouteModel.Home.ROUTE) {
        HomeScreen(navController)
    }
}