package com.workspace.core.data.repository

import com.workspace.core.data.datasource.AuthDataSource
import com.workspace.core.data.local.AuthManager
import com.workspace.core.data.mapper.toDomainModel
import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import com.workspace.core.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val authManager: AuthManager
) : AuthRepository {

    override suspend fun loginWithEmail(email: String, password: String): ServiceResult<User> {
        return when (val result = authDataSource.loginWithEmail(email, password)) {
            is ServiceResult.Success -> ServiceResult.Success(result.data.toDomainModel())
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String): ServiceResult<User> {
        return when (val result = authDataSource.signUpWithEmail(email, password)) {
            is ServiceResult.Success -> ServiceResult.Success(result.data.toDomainModel())
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): ServiceResult<User> {
        return when (val result = authDataSource.loginWithGoogle(idToken)) {
            is ServiceResult.Success -> ServiceResult.Success(result.data.toDomainModel())
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun checkUserLoggedIn(): ServiceResult<Boolean> {
        return when (val result = authDataSource.getCurrentUser()) {
            is ServiceResult.Success -> ServiceResult.Success(result.data)
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun signOut() {
        authDataSource.signOut()
        authManager.clearData()
    }

    override suspend fun sendEmailVerificationCode(): ServiceResult<Unit> {
        return when (val result = authDataSource.sendEmailVerificationCode()) {
            is ServiceResult.Success -> ServiceResult.Success(result.data)
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun deleteAccount(): ServiceResult<Unit> {
        return when (val result = authDataSource.deleteAccount()) {
            is ServiceResult.Success -> ServiceResult.Success(result.data)
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun isEmailVerified(): ServiceResult<Boolean> {
        return when (val result = authDataSource.isEmailVerified()) {
            is ServiceResult.Success -> ServiceResult.Success(result.data)
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override fun tempSaveUserEmailToSharedPreferences(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            authManager.saveTempUserEmail(email)
        }
    }

    override fun tempGetUserEmailFromSharedPreferences(): String? {
        return runBlocking {
            authManager.tempUserEmail.firstOrNull()
        }
    }

    private suspend fun saveTokenToSharedPreferences(token: String) {
        authManager.saveIdToken(token)
    }
}