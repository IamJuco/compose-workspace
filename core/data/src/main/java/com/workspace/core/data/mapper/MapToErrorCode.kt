package com.workspace.core.data.mapper

import com.workspace.core.domain.model.ErrorCode
import com.workspace.core.domain.model.ServiceResult

fun mapToErrorCode(exception: Exception): ServiceResult.Error {
    return when (exception) {
        is java.net.SocketTimeoutException, is java.io.IOException -> {
            ServiceResult.Error(ErrorCode.NETWORK_TIMEOUT, exception.message)
        }
        is retrofit2.HttpException -> {
            val errorCode = httpErrorMap[exception.code()] ?: ErrorCode.UNKNOWN_ERROR
            ServiceResult.Error(errorCode, exception.message())
        }

        else -> {
            ServiceResult.Error(ErrorCode.UNKNOWN_ERROR, exception.message)
        }
    }
}

private val httpErrorMap = mapOf(
    400 to ErrorCode.HTTP_CLIENT_BAD_REQUEST,
    401 to ErrorCode.HTTP_CLIENT_UNAUTHORIZED,
    403 to ErrorCode.HTTP_CLIENT_FORBIDDEN,
    404 to ErrorCode.HTTP_CLIENT_NOT_FOUND,
    500 to ErrorCode.HTTP_SERVER_ERROR,
    501 to ErrorCode.HTTP_SERVER_NOT_IMPLEMENTED,
    502 to ErrorCode.HTTP_SERVER_BAD_GATEWAY
)