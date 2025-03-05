package com.workspace.app.main.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.feature.detail.navigation.detailNavGraph
import com.example.feature.login.navigation.loginNavGraph
import com.example.feature.signup.navigation.emailCheckNavGraph
import com.example.feature.signup.navigation.emailVerificationNavGraph
import com.example.feature.signup.navigation.passwordCheckNavGraph
import com.workspace.app.main.navigation.MainNavigator
import com.workspace.feature.home.navigation.homeNavGraph
import com.workspace.feature.mypage.navigation.myPageNavGraph

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit,
    onShowLoginSnackBar: () -> Unit,
    loggedIn: Boolean
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        homeNavGraph(
            padding = padding,
            navigateToDetail = navigator::navigateToDetail,
        )

        myPageNavGraph(
            padding = padding,
            navigateToLogin = navigator::navigateToLogin
        )

        detailNavGraph(
            popBackStack = navigator::popBackStack,
            padding = padding,
        )

        loginNavGraph(
            padding = padding,
            onShowSnackBar = onShowSnackBar,
            navigateToHome = navigator::navigateToHome,
            navigateToEmailCheck = navigator::navigateToEmailCheck
        )

        emailCheckNavGraph(
            padding = padding,
            navigateToEmailVerification = navigator::navigateToEmailVerification,
            onShowSnackBar = onShowSnackBar,
            popBackStack = navigator::popBackStack,
        )

        emailVerificationNavGraph(
            padding = padding,
            navigateToPasswordCheck = navigator::navigateToPasswordCheck,
            onShowSnackBar = onShowSnackBar,
            popBackStack = navigator::popBackStack,
        )

        passwordCheckNavGraph(
            padding = padding,
            navigateToLogin = navigator::navigateToLogin,
            onShowSnackBar = onShowSnackBar,
        )
    }
}