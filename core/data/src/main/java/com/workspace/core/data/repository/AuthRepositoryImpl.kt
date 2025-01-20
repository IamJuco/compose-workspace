package com.workspace.core.data.repository

import com.workspace.core.data.datasource.AuthDataSource
import com.workspace.core.data.datasource.FirestoreSource
import com.workspace.core.data.local.AuthManager
import com.workspace.core.data.mapper.toDocument
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
    private val authManager: AuthManager,
    private val firestoreSource: FirestoreSource
) : AuthRepository {

    override suspend fun loginWithEmail(email: String, password: String): ServiceResult<Unit> {
        return when (val result = authDataSource.loginWithEmail(email, password)) {
            is ServiceResult.Success -> ServiceResult.Success(result.data)
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    //TODO:: 지금 임시 회원가입 때문에 User uid가 두개 저장됨
    // Usecase에서 saveUserInfo 처리하자, userRepositoryImpl 이용해서
    override suspend fun signUpWithEmail(email: String, password: String): ServiceResult<Boolean> {
        return when (val result = authDataSource.signUpWithEmail(email, password)) {
            is ServiceResult.Success -> {
                val currentUser = authDataSource.getCurrentUserUid()
                if (currentUser != null) {
                    val user = User(
                        id = currentUser,
                        email = email,
                        nickName = "과일마스터",
                        profile = null
                    )
                    when (firestoreSource.saveUserInfo(user.toDocument())) {
                        is ServiceResult.Success -> ServiceResult.Success(true)
                        is ServiceResult.Error -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR, "유저 정보 저장 실패")
                    }
                } else {
                    ServiceResult.Error(ErrorCode.UNKNOWN_ERROR, "회원가입 성공, 사용자 정보 없음")
                }
            }
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun tempSignUpWithEmail(email: String, password: String): ServiceResult<Unit> {
        return when (val result = authDataSource.signUpWithEmail(email, password)) {
            is ServiceResult.Success -> ServiceResult.Success(Unit)
            is ServiceResult.Error -> result
            else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
        }
    }

    override suspend fun checkUserLoggedIn(): ServiceResult<Boolean> {
        return when (val reloadResult = authDataSource.reloadCurrentUser()) {
            is ServiceResult.Success -> {
                when (val result = authDataSource.isCurrentUser()) {
                    is ServiceResult.Success -> ServiceResult.Success(result.data)
                    is ServiceResult.Error -> result
                    else -> ServiceResult.Error(ErrorCode.UNKNOWN_ERROR)
                }
            }
            is ServiceResult.Error -> reloadResult
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
}