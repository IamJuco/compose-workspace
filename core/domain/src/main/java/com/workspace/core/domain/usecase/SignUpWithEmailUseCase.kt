package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.repository.AuthRepository
import com.workspace.core.domain.repository.UserRepository
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    //TODO null 처리해줘야함
    // AuthRepositoryImpl 확인할것
    suspend operator fun invoke(password: String): ServiceResult<Boolean> {
        val pref = authRepository.tempGetUserEmailFromSharedPreferences()
        return authRepository.signUpWithEmail(pref!!, password)
    }
}