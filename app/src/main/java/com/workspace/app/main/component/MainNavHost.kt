package com.workspace.app.main.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.feature.detail.navigation.detailNavGraph
import com.example.feature.login.navigation.loginNavGraph
import com.example.feature.signup.navigation.signUpNavGraph
import com.workspace.app.main.navigation.MainNavigator
import com.workspace.feature.home.navigation.homeNavGraph
import com.workspace.feature.mypage.myPageNavGraph

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit,
    onShowLoginSnackBar: () -> Unit,
    hasToken: Boolean
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        homeNavGraph(
            padding = padding,
            navigateToDetail = navigator::navigateToDetail,
            onShowLoginSnackBar = onShowLoginSnackBar,
            hasToken = hasToken
        )

        myPageNavGraph(
            padding = padding
        )

        detailNavGraph(
            popBackStack = navigator::popBackStack,
            padding = padding,
            onShowLoginSnackBar = onShowLoginSnackBar,
            hasToken = hasToken
        )

        loginNavGraph(
            padding = padding,
            onShowSnackBar = onShowSnackBar,
            navigateToHome = navigator::navigateToHome
        )

        signUpNavGraph(
            padding = padding,
            navigateToLogin = navigator::navigateLogin
        )
    }
}