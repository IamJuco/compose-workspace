package com.workspace.core.data.mapper

import android.util.Log
import com.google.firebase.auth.FirebaseAuthException
import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.FirebaseAuthError
import com.workspace.core.domain.model.ServiceResult

fun mapToErrorCode(exception: Exception): ServiceResult.Error {
    if (exception is FirebaseAuthException) {
        Log.d("0526Firebase에러코드", exception.errorCode)
    }
    return when (exception) {
        is java.net.SocketTimeoutException, is java.io.IOException -> {
            ServiceResult.Error(ErrorCode.NETWORK_TIMEOUT, exception.message)
        }
        is retrofit2.HttpException -> {
            val errorCode = mapHttpErrorCode(exception.code())
            ServiceResult.Error(errorCode, exception.message())
        }
        is FirebaseAuthException -> {
            val firebaseErrorCode = mapFirebaseAuthError(exception.errorCode)
            ServiceResult.Error(ErrorCode.UNKNOWN_ERROR, firebaseErrorCode.message)
        }
        else -> {
            ServiceResult.Error(ErrorCode.UNKNOWN_ERROR, exception.message)
        }
    }
}

fun mapHttpErrorCode(code: Int): ErrorCode {
    return when (code) {
        400 -> ErrorCode.HTTP_CLIENT_BAD_REQUEST
        401 -> ErrorCode.HTTP_CLIENT_UNAUTHORIZED
        403 -> ErrorCode.HTTP_CLIENT_FORBIDDEN
        404 -> ErrorCode.HTTP_CLIENT_NOT_FOUND
        500 -> ErrorCode.HTTP_SERVER_ERROR
        501 -> ErrorCode.HTTP_SERVER_NOT_IMPLEMENTED
        502 -> ErrorCode.HTTP_SERVER_BAD_GATEWAY
        else -> ErrorCode.UNKNOWN_ERROR
    }
}

fun mapFirebaseAuthError(errorCode: String): FirebaseAuthError {
    return when (errorCode) {
        "ERROR_INVALID_EMAIL" -> FirebaseAuthError.INVALID_EMAIL
        "ERROR_USER_DISABLED" -> FirebaseAuthError.USER_DISABLED
        "ERROR_USER_NOT_FOUND" -> FirebaseAuthError.USER_NOT_FOUND
        "ERROR_WRONG_PASSWORD" -> FirebaseAuthError.WRONG_PASSWORD
        "ERROR_EMAIL_ALREADY_IN_USE" -> FirebaseAuthError.EMAIL_ALREADY_IN_USE
        "ERROR_OPERATION_NOT_ALLOWED" -> FirebaseAuthError.OPERATION_NOT_ALLOWED
        "ERROR_WEAK_PASSWORD" -> FirebaseAuthError.WEAK_PASSWORD
        "ERROR_INVALID_CREDENTIAL" -> FirebaseAuthError.INVALID_CREDENTIAL
        "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> FirebaseAuthError.ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL
        "ERROR_CREDENTIAL_ALREADY_IN_USE" -> FirebaseAuthError.CREDENTIAL_ALREADY_IN_USE
        "ERROR_REQUIRES_RECENT_LOGIN" -> FirebaseAuthError.REQUIRES_RECENT_LOGIN
        "ERROR_TOO_MANY_REQUESTS" -> FirebaseAuthError.TOO_MANY_REQUESTS
        "ERROR_PROVIDER_ALREADY_LINKED" -> FirebaseAuthError.PROVIDER_ALREADY_LINKED
        "ERROR_NO_SUCH_PROVIDER" -> FirebaseAuthError.NO_SUCH_PROVIDER
        "ERROR_INVALID_USER_TOKEN" -> FirebaseAuthError.INVALID_USER_TOKEN
        "ERROR_USER_TOKEN_EXPIRED" -> FirebaseAuthError.USER_TOKEN_EXPIRED
        "ERROR_NETWORK_REQUEST_FAILED" -> FirebaseAuthError.NETWORK_REQUEST_FAILED
        "ERROR_INTERNAL_ERROR" -> FirebaseAuthError.INTERNAL_ERROR
        else -> FirebaseAuthError.UNKNOWN_ERROR
    }
}