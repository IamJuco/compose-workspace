package com.workspace.app.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.workspace.app.main.component.BottomNavigationBar
import com.workspace.app.main.component.MainNavHost
import com.workspace.app.main.navigation.MainNavigator
import com.workspace.app.main.navigation.rememberMainNavigator
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
    viewModel: MainViewModel = hiltViewModel()
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val snackBarHostState = remember { SnackbarHostState() }

    val initialScreenStateState = viewModel.initialScreenState.collectAsStateWithLifecycle()
    val initialScreenState = initialScreenStateState.value

    val onShowSnackBar: (String) -> Unit = { msg ->
        lifecycleScope.launch { snackBarHostState.showSnackbar(msg) }
    }

    LaunchedEffect(initialScreenState) {
        when (initialScreenState) {
            MainViewModel.ScreenState.Home -> navigator.navigateToHome()
            MainViewModel.ScreenState.Login -> navigator.navigateLogin()
            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        content = { paddingValues ->
            MainNavHost(
                navigator = navigator,
                padding = paddingValues,
                onShowSnackBar = onShowSnackBar
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