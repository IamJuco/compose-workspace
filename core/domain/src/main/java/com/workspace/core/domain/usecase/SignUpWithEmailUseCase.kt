package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import com.workspace.core.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    //TODO null 처리해줘야함
    suspend operator fun invoke(password: String): ServiceResult<User> {
        val pref = repository.tempGetUserEmailFromSharedPreferences()
        return repository.signUpWithEmail(pref!!, password)
    }
}