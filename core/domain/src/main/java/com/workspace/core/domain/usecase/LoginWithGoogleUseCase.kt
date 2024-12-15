package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import com.workspace.core.domain.repository.AuthRepository

class LoginWithGoogleUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): ServiceResult<User> {
        return repository.loginWithGoogle(idToken)
    }
}