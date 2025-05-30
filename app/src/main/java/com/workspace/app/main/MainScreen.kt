package com.workspace.app.main

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.workspace.app.main.component.BottomNavigationBar
import com.workspace.app.main.component.MainNavHost
import com.workspace.app.main.navigation.MainNavigator
import com.workspace.app.main.navigation.rememberMainNavigator
import com.workspace.core.domain.model.ErrorCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
    viewModel: MainViewModel = hiltViewModel()
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val snackBarHostState = remember { SnackbarHostState() }

    val initialScreenState by viewModel.initialScreenState.collectAsStateWithLifecycle()
    val loggedIn by viewModel.loginState.collectAsStateWithLifecycle()
    Log.d("0526Token", loggedIn.toString())

    val onShowLoginSnackBar: () -> Unit = {
        lifecycleScope.launch {
            snackBarHostState.showSnackbar(
                message = "로그인 후 이용 가능한 서비스입니다.",
                actionLabel = "로그인",
                duration = SnackbarDuration.Short
            ).let { result ->
                if (result == SnackbarResult.ActionPerformed) {
                    navigator.navigateToLogin()
                }
            }
        }
    }

    val onShowSnackBar: (String) -> Unit = { msg ->
        lifecycleScope.launch { snackBarHostState.showSnackbar(msg) }
    }

    LaunchedEffect(initialScreenState) {
        if (initialScreenState is MainViewModel.ScreenState.Loading) {
            delay(5_000)
            onShowSnackBar(ErrorCode.NETWORK_TIMEOUT.message)
            navigator.navigateToLogin()
        }
        when {
            initialScreenState is MainViewModel.ScreenState.Home -> navigator.navigateToHome()
            initialScreenState is MainViewModel.ScreenState.Login -> navigator.navigateToLogin()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        content = { paddingValues ->
            MainNavHost(
                navigator = navigator,
                padding = paddingValues,
                onShowSnackBar = onShowSnackBar,
                onShowLoginSnackBar = onShowLoginSnackBar,
                loggedIn = loggedIn
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = navigator.currentMenu != null,
                enter = EnterTransition.None,
                exit = ExitTransition.None
            ) {
                BottomNavigationBar(
                    currentMenu = navigator.currentMenu,
                    onMenuSelected = { navigator.navigate(it) },
                )
            }
        }
    )
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}