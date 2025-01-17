package com.workspace.core.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.workspace.core.data.document.UserDocument
import com.workspace.core.data.mapper.mapToErrorCode
import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.ServiceResult
import com.workspace.core.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FirestoreSource {
    suspend fun saveUserInfo(userDocument: UserDocument): ServiceResult<Unit>
    suspend fun getUserInfo(userId: String): ServiceResult<User>
}

class FirestoreSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreSource {
    override suspend fun saveUserInfo(userDocument: UserDocument): ServiceResult<Unit> {
        return try {
            firestore.collection("users")
                .document(userDocument.id)
                .set(userDocument)
                .await()
            ServiceResult.Success(Unit)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }

    override suspend fun getUserInfo(userId: String): ServiceResult<User> {
        return try {
            val documentSnapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()
            val user = documentSnapshot.toObject(User::class.java)
            if (user != null) {
                ServiceResult.Success(user)
            } else {
                ServiceResult.Error(ErrorCode.HTTP_CLIENT_NOT_FOUND, "요청한 리소스를 찾을 수 없습니다.")
            }
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }
}