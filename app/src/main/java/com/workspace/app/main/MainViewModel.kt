package com.workspace.app.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.usecase.CheckUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase
) : ViewModel() {

    private val _initialScreenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val initialScreenState: StateFlow<ScreenState> = _initialScreenState

    private val _loginState = MutableStateFlow(false)
    val loginState = _loginState.asStateFlow()

    init {
        initScreen()
    }

    private fun initScreen() {
        viewModelScope.launch {
            val result = checkUserLoggedInUseCase()
            _initialScreenState.update {
                when (result) {
                    is ServiceResult.Success -> {
                        if (result.data) {
                            _loginState.update { true }
                            ScreenState.Home
                        } else {
                            _loginState.update { false }
                            ScreenState.Login
                        }
                    }

                    is ServiceResult.Error -> {
                        _loginState.update { false }
                        ScreenState.Login
                    }
                }
            }
        }
    }

    sealed class ScreenState {
        data object Loading : ScreenState()
        data object Home : ScreenState()
        data object Login : ScreenState()
    }
}