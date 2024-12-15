package com.workspace.core.domain.usecase

import com.workspace.core.domain.repository.AuthRepository

class SignOutWithEmailUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.signOut()
    }
}