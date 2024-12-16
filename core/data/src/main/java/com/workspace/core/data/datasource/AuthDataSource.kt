package com.workspace.core.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.workspace.core.data.mapper.mapToErrorCode
import com.workspace.core.domain.model.ServiceResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthDataSource {
    suspend fun loginWithEmail(email: String, password: String): ServiceResult<FirebaseUser>
    suspend fun signUpWithEmail(email: String, password: String): ServiceResult<FirebaseUser>
    suspend fun loginWithGoogle(idToken: String): ServiceResult<FirebaseUser>
    suspend fun getCurrentUser(): ServiceResult<FirebaseUser?>
    suspend fun signOut()
}

class AuthDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthDataSource {
    override suspend fun loginWithEmail(email: String, password: String): ServiceResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            ServiceResult.Success(result.user!!)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String): ServiceResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            ServiceResult.Success(result.user!!)
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

    override suspend fun getCurrentUser(): ServiceResult<FirebaseUser?> {
        return try {
            ServiceResult.Success(firebaseAuth.currentUser)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}