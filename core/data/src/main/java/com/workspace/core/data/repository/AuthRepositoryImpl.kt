package com.workspace.core.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.workspace.core.data.constants.Constants
import com.workspace.core.data.datasource.AuthDataSource
import com.workspace.core.data.mapper.toDomainModel
import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import com.workspace.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override suspend fun loginWithEmail(email: String, password: String): ServiceResult<User> {
        return when (val result = authDataSource.loginWithEmail(email, password)) {
            is ServiceResult.Success -> {
                val tokenResult = authDataSource.getIdToken()
                val token = (tokenResult as? ServiceResult.Success)?.data
                Log.d("0526Token", tokenResult.toString())
                saveTokenToSharedPreferences(token)
                ServiceResult.Success(result.data.toDomainModel())
            }
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

    override suspend fun signOut() {
        authDataSource.signOut()
        sharedPreferences.edit().remove(Constants.FIREBASE_USER_TOKEN).apply()
    }

    override suspend fun checkTokenForExpire(): Boolean {
        //TODO 로직 수정할것 ( error에 대한 대응할것 )
        val savedToken = getTokenFromSharedPreferences()
        val currentUserResult = authDataSource.getCurrentUser()

        return if (savedToken != null && currentUserResult is ServiceResult.Success && currentUserResult.data != null) {
            try {
                val refreshedTokenResult = authDataSource.getIdToken()
                if (refreshedTokenResult is ServiceResult.Success) {
                    val refreshedToken = refreshedTokenResult.data
                    if (refreshedToken == savedToken) {
                        true
                    } else {
                        signOut()
                        false
                    }
                } else {
                    signOut()
                    false
                }
            } catch (e: Exception) {
                signOut()
                false
            }
        } else {
            false
        }
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
        sharedPreferences.edit().putString(Constants.FIREBASE_TEMP_SAVE_USER_EMAIL, email).apply()
    }

    private fun saveTokenToSharedPreferences(token: String?) {
        sharedPreferences.edit().putString(Constants.FIREBASE_USER_TOKEN, token).apply()
    }

    private fun getTokenFromSharedPreferences(): String? {
        val token = sharedPreferences.getString(Constants.FIREBASE_USER_TOKEN, null)
        return token
    }
}