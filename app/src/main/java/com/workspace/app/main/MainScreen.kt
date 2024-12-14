package com.workspace.app.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.workspace.app.main.component.BottomNavigationBar
import com.workspace.app.main.component.MainNavGraph
import com.workspace.app.main.navigation.MainNavigator
import com.workspace.app.main.navigation.rememberMainNavigator

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    Scaffold(
        content = { paddingValues ->
            MainNavGraph(
                navigator = navigator,
                padding = paddingValues
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentMenu = navigator.currentMenu,
                onMenuSelected = { navigator.navigate(it) }
            )
        }
    )
}