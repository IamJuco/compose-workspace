package com.example.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.UiState
import com.workspace.core.domain.usecase.DeleteTempAccountUseCase
import com.workspace.core.domain.usecase.SignUpAndSendVerificationCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpAndSendVerificationCodeUseCase: SignUpAndSendVerificationCodeUseCase,
    private val deleteTempAccountUseCase: DeleteTempAccountUseCase
) : ViewModel() {
    private val _verificationEmailState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val verificationEmailState: StateFlow<UiState<Unit>> = _verificationEmailState

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

    // Firebase의 한계로 이메일 인증시 임시 회원가입을 무조건 시켜야하므로
    // 계정 인증 하자마자 계정 삭제
    fun resetTempSignUp() {
        _verificationEmailState.update { UiState.Idle }
        viewModelScope.launch {
            deleteTempAccountUseCase()
        }
    }
}