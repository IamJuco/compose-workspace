package com.workspace.core.domain.repository

import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User

interface UserRepository {
    suspend fun saveUserInfo(user: User): ServiceResult<Unit>
    suspend fun getUserInfo(): ServiceResult<User>
    suspend fun updateUserProfile(profileImage: String): ServiceResult<String>
}