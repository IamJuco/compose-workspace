package com.workspace.core.domain.repository

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User

interface AuthRepository {
    suspend fun loginWithEmail(email: String, password: String): ServiceResult<User>
    suspend fun signUpWithEmail(email: String, password: String): ServiceResult<User>
    suspend fun loginWithGoogle(idToken: String): ServiceResult<User>
    suspend fun getCurrentUser(): ServiceResult<User?>
    suspend fun signOut()
}