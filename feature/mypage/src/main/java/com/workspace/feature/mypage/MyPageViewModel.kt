package com.workspace.feature.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.UiState
import com.workspace.core.domain.usecase.GetUserProfileUseCase
import com.workspace.core.domain.usecase.UpdateUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String?>>(UiState.Idle)
    val uiState: StateFlow<UiState<String?>> = _uiState

    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = getUserProfileUseCase()) {
                is ServiceResult.Success -> _uiState.update { UiState.Success(result.data.profile) }
                is ServiceResult.Error -> _uiState.update {
                    UiState.Error(
                        result.errorCode,
                        result.errorMessage
                    )
                }
            }
        }
    }

    fun updateProfileImage(imagePath: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = updateUserProfileUseCase(imagePath)) {
                is ServiceResult.Success -> _uiState.update { UiState.Success(result.data) }
                is ServiceResult.Error -> _uiState.update {
                    UiState.Error(
                        result.errorCode,
                        result.errorMessage
                    )
                }
            }
        }
    }
}