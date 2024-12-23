package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.repository.AuthRepository
import com.workspace.core.domain.utils.TempPassword
import javax.inject.Inject

class SignUpAndSendVerificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): ServiceResult<Unit> {
        val temporaryPassword = TempPassword.tempRandomPassword()

        val signUpResult = authRepository.signUpWithEmail(email, temporaryPassword)
        if (signUpResult is ServiceResult.Error) {
            return signUpResult
        }

        return authRepository.sendEmailVerificationCode()
    }
}