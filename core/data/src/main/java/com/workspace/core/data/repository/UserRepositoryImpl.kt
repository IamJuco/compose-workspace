package com.workspace.core.data.repository

import com.workspace.core.data.datasource.FirestoreSource
import com.workspace.core.data.mapper.toDocument
import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import com.workspace.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: FirestoreSource
) : UserRepository{
    override suspend fun saveUserInfo(user: User): ServiceResult<Unit> {
        return when (val result = userDataSource.saveUserInfo(user.toDocument())) {
            is ServiceResult.Success -> ServiceResult.Success(result.data)
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun getUserInfo(userId: String): ServiceResult<User> {
        return when (val result = userDataSource.getUserInfo(userId)) {
            is ServiceResult.Success -> ServiceResult.Success(result.data)
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }
}