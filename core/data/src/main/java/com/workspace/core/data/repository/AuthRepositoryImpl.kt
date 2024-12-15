package com.workspace.core.data.repository

import com.workspace.core.data.datasource.AuthDataSource
import com.workspace.core.data.mapper.toDomainModel
import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import com.workspace.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override suspend fun loginWithEmail(email: String, password: String): ServiceResult<User> {
        return when (val result = authDataSource.loginWithEmail(email, password)) {
            is ServiceResult.Success -> ServiceResult.Success(result.data.toDomainModel())
            is ServiceResult.Error -> ServiceResult.Error(result.error)
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String): ServiceResult<User> {
        return when (val result = authDataSource.signUpWithEmail(email, password)) {
            is ServiceResult.Success -> ServiceResult.Success(result.data.toDomainModel())
            is ServiceResult.Error -> ServiceResult.Error(result.error)
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): ServiceResult<User> {
        return when (val result = authDataSource.loginWithGoogle(idToken)) {
            is ServiceResult.Success -> ServiceResult.Success(result.data.toDomainModel())
            is ServiceResult.Error -> ServiceResult.Error(result.error)
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun getCurrentUser(): ServiceResult<User?> {
        return when (val result = authDataSource.getCurrentUser()) {
            is ServiceResult.Success -> ServiceResult.Success(result.data?.toDomainModel())
            is ServiceResult.Error -> ServiceResult.Error(result.error)
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun signOut() {
        authDataSource.signOut()
    }
}