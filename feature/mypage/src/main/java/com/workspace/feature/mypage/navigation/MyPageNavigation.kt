package com.workspace.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.workspace.core.domain.navigation.MainMenuRoute
import com.workspace.feature.mypage.MyPageRoute

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(MainMenuRoute.MyPage, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues
) {
    composable<MainMenuRoute.MyPage> {
        MyPageRoute(
            padding = padding
        )
    }
}