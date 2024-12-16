package com.example.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import com.workspace.core.domain.usecase.LoginWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithEmailUseCase: LoginWithEmailUseCase
) : ViewModel(){
    private val _loginWithEmailState = MutableStateFlow<ServiceResult<User>?>(null)
    val loginWithEmailState: StateFlow<ServiceResult<User>?> = _loginWithEmailState

    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _loginWithEmailState.update { loginWithEmailUseCase(email, password) }
        }
    }
}