package com.workspace.app.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.workspace.core.domain.navigation.RouteModel
import com.workspace.feature.home.navigation.homeRoute
import com.workspace.feature.mypage.myPageRoute

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = RouteModel.Home.ROUTE,
        modifier = modifier
    ) {
        homeRoute()
        myPageRoute()
    }
}