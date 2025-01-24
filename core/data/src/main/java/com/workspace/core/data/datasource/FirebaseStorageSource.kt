package com.workspace.core.data.datasource

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.workspace.core.data.mapper.mapToErrorCode
import com.workspace.core.domain.model.ServiceResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FirebaseStorageSource {
    suspend fun uploadImage(userId: String, imageUri: Uri): ServiceResult<String>
}

class FirebaseStorageSourceImpl @Inject constructor(
    private val storage: FirebaseStorage
) : FirebaseStorageSource {
    override suspend fun uploadImage(userId: String, imageUri: Uri): ServiceResult<String> {
        return try {
            val ref = storage.reference.child("profile_images/$userId/${imageUri.lastPathSegment}")
            ref.putFile(imageUri).await()
            val downloadUrl = ref.downloadUrl.await().toString()
            ServiceResult.Success(downloadUrl)
        } catch (e: Exception) {
            mapToErrorCode(e)
        }
    }
}