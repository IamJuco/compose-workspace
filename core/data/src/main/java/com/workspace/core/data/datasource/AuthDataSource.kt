package com.workspace.core.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.workspace.core.data.mapper.mapToErrorCode
import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.ServiceResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthDataSource {
    suspend fun loginWithEmail(email: String, password: String): ServiceResult<Unit>
    suspend fun signUpWithEmail(email: String, password: String): ServiceResult<Boolean>
    suspend fun loginWithGoogle(idToken: String): ServiceResult<FirebaseUser>
    suspend fun isCurrentUser(): ServiceResult<Boolean>
    suspend fun getCurrentUserUid(): String?
    suspend fun signOut(): ServiceResult<Unit>
    suspend fun getIdToken(): ServiceResult<String?>
    suspend fun sendEmailVerificationCode(): ServiceResult<Unit>
    suspend fun deleteAccount(): ServiceResult<Unit>
    suspend fun isEmailVerified(): ServiceResult<Boolean>
    suspend fun reloadCurrentUser(): ServiceResult<Unit>
}

class AuthDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthDataSource {
    override suspend fun loginWithEmail(
        email: String,
        password: String
    ): ServiceResult<Unit> {
        //TODO 나중에 user 가 null 일때 처리 할 것
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            ServiceResult.Success(Unit)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String
    ): ServiceResult<Boolean> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                ServiceResult.Success(true)
            } else {
                ServiceResult.Error(ErrorCode.UNKNOWN_ERROR, "회원가입 실패: 사용자 정보가 없습니다.")
            }
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): ServiceResult<FirebaseUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            ServiceResult.Success(result.user!!)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun isCurrentUser(): ServiceResult<Boolean> {
        val user = firebaseAuth.currentUser != null
        return try {
            ServiceResult.Success(user)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun reloadCurrentUser(): ServiceResult<Unit> {
        return try {
            firebaseAuth.currentUser?.reload()?.await()
            ServiceResult.Success(Unit)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun getIdToken(): ServiceResult<String?> {
        return try {
            // true = 항상 새로운 토크 반환
            // false = 캐시된 토큰만 반환 ( 기본 토큰 만료 되면 새로운 토큰 반환 )
            val token = firebaseAuth.currentUser?.getIdToken(false)?.await()?.token
            ServiceResult.Success(token)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun sendEmailVerificationCode(): ServiceResult<Unit> {
        val user = firebaseAuth.currentUser
        return try {
            user!!.sendEmailVerification().await()
            ServiceResult.Success(Unit)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun isEmailVerified(): ServiceResult<Boolean> {
        val user = firebaseAuth.currentUser
        return try {
            val result = user!!.isEmailVerified
            ServiceResult.Success(result)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun deleteAccount(): ServiceResult<Unit> {
        val user = firebaseAuth.currentUser
        return try {
            user!!.delete().await()
            ServiceResult.Success(Unit)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun signOut(): ServiceResult<Unit> {
        return try {
            firebaseAuth.signOut()
            ServiceResult.Success(Unit)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }
}