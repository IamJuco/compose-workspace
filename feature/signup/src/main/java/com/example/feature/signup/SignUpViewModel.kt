package com.example.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.UiState
import com.workspace.core.domain.model.User
import com.workspace.core.domain.usecase.SignUpWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase
) : ViewModel() {
    private val _signUpState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val signUpState: StateFlow<UiState<User>> = _signUpState

    fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _signUpState.update { UiState.Loading }
            val result = signUpWithEmailUseCase(email, password)
            _signUpState.update {
                when (result) {
                    is ServiceResult.Success -> UiState.Success(result.data)
                    is ServiceResult.Error -> UiState.Error(result.errorCode, result.errorMessage)
                }
            }
        }
    }
}