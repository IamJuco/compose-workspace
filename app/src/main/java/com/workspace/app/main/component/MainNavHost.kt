package com.workspace.app.main.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.feature.detail.navigation.detailNavGraph
import com.example.feature.login.navigation.loginNavGraph
import com.workspace.app.main.navigation.MainNavigator
import com.workspace.feature.home.navigation.homeNavGraph
import com.workspace.feature.mypage.myPageNavGraph

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        homeNavGraph(
            padding = padding,
            navigateToDetail = navigator::navigateToDetail
        )

        myPageNavGraph(
            padding = padding
        )

        detailNavGraph(
            popBackStack = navigator::popBackStack,
            padding = padding
        )

        loginNavGraph(
            padding = padding,
            onShowSnackBar = onShowSnackBar,
            navigateToHome = navigator::navigateToHome
        )
    }
}