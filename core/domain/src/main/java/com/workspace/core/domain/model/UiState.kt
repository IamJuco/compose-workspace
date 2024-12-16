package com.workspace.core.domain.model

sealed class UiState<out T> {
    data object Idle : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val errorCode: ErrorCode, val errorMessage: String? = null) : UiState<Nothing>()
}