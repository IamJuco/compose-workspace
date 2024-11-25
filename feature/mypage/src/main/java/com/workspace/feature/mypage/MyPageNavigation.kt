package com.workspace.feature.mypage

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.workspace.core.domain.navigation.RouteModel

fun NavGraphBuilder.myPageRoute(navController: NavHostController) {
    composable(RouteModel.MyPage.ROUTE) {
        MyPageScreen(navController)
    }
}