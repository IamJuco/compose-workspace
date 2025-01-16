package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.repository.AuthRepository
import javax.inject.Inject

class IsEmailVerifiedUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): ServiceResult<Boolean> {
        return authRepository.isEmailVerified()
    }
}