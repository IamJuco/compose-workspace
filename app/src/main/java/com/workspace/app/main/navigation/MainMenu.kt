package com.workspace.app.main.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.workspace.app.R
import com.workspace.core.domain.navigation.MainMenuRoute

enum class MainMenu(
    @DrawableRes
    val iconResId: Int,
    val contentDescription: String,
    val route: MainMenuRoute,
) {
    HOME(
        iconResId = R.drawable.ic_home_24dp,
        contentDescription = "Home",
        route = MainMenuRoute.Home,
    ),
    MyPage(
        iconResId = R.drawable.ic_my_page_24dp,
        contentDescription = "MyPage",
        route = MainMenuRoute.MyPage,
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainMenuRoute) -> Boolean): MainMenu? {
            return entries.find { predicate(it.route) }
        }
    }
}