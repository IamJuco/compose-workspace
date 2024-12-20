package com.workspace.core.domain.usecase

import com.workspace.core.domain.repository.AuthRepository
import javax.inject.Inject

class CheckUserAccessTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.checkTokenForExpire()
    }
}
