package com.workspace.core.domain.repository

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User

interface AuthRepository {
    suspend fun loginWithEmail(email: String, password: String): ServiceResult<Unit>
    suspend fun signUpWithEmail(email: String, password: String): ServiceResult<Boolean>
    suspend fun tempSignUpWithEmail(email: String, password: String): ServiceResult<Unit>
    suspend fun checkUserLoggedIn(): ServiceResult<Boolean>
    suspend fun signOut(): ServiceResult<Unit>
    suspend fun sendEmailVerificationCode(): ServiceResult<Unit>
    suspend fun deleteAccount(): ServiceResult<Unit>
    suspend fun isEmailVerified(): ServiceResult<Boolean>
    fun tempSaveUserEmailToSharedPreferences(email: String)
    fun tempGetUserEmailFromSharedPreferences(): String?
}