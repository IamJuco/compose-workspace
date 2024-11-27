package com.workspace.app.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.workspace.app.main.component.BottomNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        content = { paddingValues ->
            MainNavGraph(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}