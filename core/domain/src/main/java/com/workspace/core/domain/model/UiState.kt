package com.workspace.core.domain.model

import com.workspace.core.domain.api_error_handle.ApiException

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Failure(val exception: ApiException) : UiState<Nothing>
}