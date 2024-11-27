package com.workspace.app.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.workspace.core.domain.navigation.RouteModel
import com.workspace.feature.home.homeRoute
import com.workspace.feature.mypage.myPageRoute

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = RouteModel.Home.ROUTE, // 첫 화면 설정
        modifier = modifier
    ) {
        // Feature 모듈의 확장 함수 호출
        homeRoute(navController)
        myPageRoute(navController)
    }
}