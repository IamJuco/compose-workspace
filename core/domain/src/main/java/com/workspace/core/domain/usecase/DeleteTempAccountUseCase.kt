package com.workspace.core.domain.usecase

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.repository.AuthRepository
import javax.inject.Inject

class DeleteTempAccountUseCase @Inject constructor(
    private val repository: AuthRepository
){
    suspend operator fun invoke(): ServiceResult<Unit> {
        return repository.deleteAccount()
    }
}