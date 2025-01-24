package com.workspace.core.data.repository

import android.net.Uri
import android.util.Log
import com.workspace.core.data.datasource.AuthDataSource
import com.workspace.core.data.datasource.FirebaseStorageSource
import com.workspace.core.data.datasource.FirestoreSource
import com.workspace.core.data.document.UserDocument
import com.workspace.core.data.mapper.toDocument
import com.workspace.core.data.mapper.toDomain
import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import com.workspace.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestoreSource: FirestoreSource,
    private val fireStorage: FirebaseStorageSource,
    private val authDataSource: AuthDataSource
) : UserRepository{
    override suspend fun saveUserInfo(user: User): ServiceResult<Unit> {
        return when (val result = firestoreSource.saveUserInfo(user.toDocument())) {
            is ServiceResult.Success -> ServiceResult.Success(result.data)
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun getUserInfo(): ServiceResult<User> {
        val currentUserUid = authDataSource.getCurrentUserUid()
            ?: return ServiceResult.Error(ErrorCode.HTTP_CLIENT_UNAUTHORIZED, "로그인된 사용자가 없습니다.")

        return when (val result = firestoreSource.getUserInfo(currentUserUid)) {
            is ServiceResult.Success -> {
                Log.d("0526getUserSuccess", result.data.toString()) // 디버깅 로그 추가
                ServiceResult.Success(result.data.toDomain())
            }
            is ServiceResult.Error -> {
                Log.d("0526getUserError", result.errorMessage.toString()) // 디버깅 로그 추가
                result
            }
        }
    }

    override suspend fun updateUserProfile(profileImage: String): ServiceResult<String> {
        val currentUserUid = authDataSource.getCurrentUserUid()
            ?: return ServiceResult.Error(ErrorCode.HTTP_CLIENT_UNAUTHORIZED, "로그인된 사용자가 없습니다.")
        val uploadResult = fireStorage.uploadImage(currentUserUid, Uri.parse(profileImage))
        Log.d("0526imageResult", uploadResult.toString())
        return when (uploadResult) {
            is ServiceResult.Success -> {
                val imageUrl = uploadResult.data

                val userDocument = UserDocument(
                    id = currentUserUid,
                    profile = imageUrl
                )

                when (val saveResult = firestoreSource.saveUserInfo(userDocument)) {
                    is ServiceResult.Success -> ServiceResult.Success(imageUrl)
                    is ServiceResult.Error -> saveResult
                }
            }
            is ServiceResult.Error -> uploadResult
        }
    }
}