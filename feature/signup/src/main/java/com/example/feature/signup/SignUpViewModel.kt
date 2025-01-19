package com.example.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.UiState
import com.workspace.core.domain.usecase.DeleteTempAccountUseCase
import com.workspace.core.domain.usecase.IsEmailVerifiedUseCase
import com.workspace.core.domain.usecase.SignUpAndSendVerificationCodeUseCase
import com.workspace.core.domain.usecase.SignUpWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpAndSendVerificationCodeUseCase: SignUpAndSendVerificationCodeUseCase,
    private val deleteTempAccountUseCase: DeleteTempAccountUseCase,
    private val isEmailVerifiedUseCase: IsEmailVerifiedUseCase,
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase
) : ViewModel() {
    private val _verificationEmailState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val verificationEmailState: StateFlow<UiState<Unit>> = _verificationEmailState

    private val _isEmailVerifiedState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val isEmailVerifiedState: StateFlow<UiState<Boolean>> = _isEmailVerifiedState

    private val _signUpState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val signUpState: StateFlow<UiState<Boolean>> = _signUpState

    fun sendVerificationEmail(email: String) {
        viewModelScope.launch {
            _verificationEmailState.update { UiState.Loading }
            when (val result = signUpAndSendVerificationCodeUseCase(email)) {
                is ServiceResult.Success -> _verificationEmailState.update { UiState.Success(result.data) }
                is ServiceResult.Error -> _verificationEmailState.update {
                    UiState.Error(
                        result.errorCode,
                        result.errorMessage
                    )
                }
            }
        }
    }

    fun checkEmailVerified() {
        viewModelScope.launch {
            _isEmailVerifiedState.update { UiState.Loading }
            when (val result = isEmailVerifiedUseCase()) {
                is ServiceResult.Success -> _isEmailVerifiedState.update { UiState.Success(result.data) }
                is ServiceResult.Error -> _isEmailVerifiedState.update {
                    UiState.Error(
                        result.errorCode,
                        result.errorMessage
                    )
                }
            }
        }
    }

    fun signUpWithEmail(password: String) {
        viewModelScope.launch {
            _signUpState.update { UiState.Loading }
            when (val result = signUpWithEmailUseCase(password)) {
                is ServiceResult.Success -> _signUpState.update { UiState.Success(result.data) }
                is ServiceResult.Error -> _signUpState.update {
                    UiState.Error(
                        result.errorCode,
                        result.errorMessage
                    )
                }
            }
        }
    }

    fun deleteTempAccount() {
        viewModelScope.launch {
            deleteTempAccountUseCase()
        }
    }

    fun resetVerificationEmailState() {
        _verificationEmailState.update { UiState.Idle }
    }

    fun resetEmailVerifiedState() {
        _isEmailVerifiedState.update { UiState.Idle }
    }
}