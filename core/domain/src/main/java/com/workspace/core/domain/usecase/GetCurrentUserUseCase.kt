package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import com.workspace.core.domain.repository.AuthRepository

class GetCurrentUserUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): ServiceResult<User?> {
        return repository.getCurrentUser()
    }
}