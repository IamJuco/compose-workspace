package com.workspace.app.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.feature.detail.navigation.navigateToDetail
import com.example.feature.login.navigation.navigateLogin
import com.workspace.core.domain.navigation.RouteModel
import com.workspace.feature.home.navigation.navigateHome
import com.workspace.feature.mypage.navigateMyPage

class MainNavigator(
    val navController: NavHostController
) {
    val startDestination = RouteModel.Login
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    val currentMenu: MainMenu?
        @Composable get() = MainMenu.find { m ->
            currentDestination?.hasRoute(m::class) == true
        }
    private val singleTopOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    }

    fun navigate(menu: MainMenu) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = menu == MainMenu.HOME
            }
            launchSingleTop = true
        }

        when (menu) {
            MainMenu.HOME -> navController.navigateHome(navOptions)
            MainMenu.MyPage -> navController.navigateMyPage(navOptions)
        }
    }

    fun navigateToDetail(pokemonId: Int) = navController.navigateToDetail(pokemonId = pokemonId, navOptions  = singleTopOptions)
    fun navigateLogin() = navController.navigateLogin(navOptions  = singleTopOptions)
//    fun navigateToSignUp() = navController.navigateToSignUp(navOptions  = singleTopOptions)
    fun navigateToHome() = navController.navigateHome(navOptions = singleTopOptions)

    fun popBackStack() {
        navController.popBackStack()
    }

    fun popBackStack(destination: RouteModel) {
        navController.popBackStack(destination, false)
    }

    @Composable
    fun isShowBottomBar() = MainMenu.find {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}