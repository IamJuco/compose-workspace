package com.workspace.app.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workspace.core.domain.usecase.CheckUserAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkUserAccessTokenUseCase: CheckUserAccessTokenUseCase
) : ViewModel() {

    private val _initialScreenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val initialScreenState: StateFlow<ScreenState> = _initialScreenState

    init {
        checkUserAccessToken()
    }

    private fun checkUserAccessToken() {
        viewModelScope.launch {
            val isTokenValid = checkUserAccessTokenUseCase()
            _initialScreenState.value = if (isTokenValid) {
                ScreenState.Home
            } else {
                ScreenState.Login
            }
        }
    }

    sealed class ScreenState {
        data object Loading : ScreenState()
        data object Home : ScreenState()
        data object Login : ScreenState()
    }
}