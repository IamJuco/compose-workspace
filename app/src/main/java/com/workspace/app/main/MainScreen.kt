package com.workspace.app.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.workspace.app.main.component.BottomNavigationBar
import com.workspace.app.main.component.MainNavHost
import com.workspace.app.main.navigation.MainNavigator
import com.workspace.app.main.navigation.rememberMainNavigator

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    Scaffold(
        content = { paddingValues ->
            MainNavHost(
                navigator = navigator,
                padding = paddingValues
            )
        },
        bottomBar = {
            // if로 설정하면 Bar가 없어졌다 사라지는 모션으로 보여서 visible로 대체
            AnimatedVisibility(
                visible = navigator.currentMenu != null
            ) {
                BottomNavigationBar(
                    currentMenu = navigator.currentMenu,
                    onMenuSelected = { navigator.navigate(it) }
                )
            }
        }
    )
}