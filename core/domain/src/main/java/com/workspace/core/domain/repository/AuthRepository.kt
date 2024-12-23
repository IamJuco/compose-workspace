package com.workspace.core.domain.repository

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User

interface AuthRepository {
    suspend fun loginWithEmail(email: String, password: String): ServiceResult<User>
    suspend fun signUpWithEmail(email: String, password: String): ServiceResult<User>
    suspend fun loginWithGoogle(idToken: String): ServiceResult<User>
    suspend fun signOut()
    suspend fun checkTokenForExpire(): Boolean
    suspend fun sendEmailVerificationCode(): ServiceResult<Unit>
    suspend fun deleteAccount(): ServiceResult<Unit>
    suspend fun isEmailVerified(): ServiceResult<Boolean>
    fun tempSaveUserEmailToSharedPreferences(email: String)
    fun tempGetUserEmailFromSharedPreferences(): String?
}