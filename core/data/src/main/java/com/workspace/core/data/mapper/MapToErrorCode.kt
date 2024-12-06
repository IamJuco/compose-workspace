package com.workspace.core.data.mapper

import com.workspace.core.domain.model.ErrorCode

fun mapToErrorCode(exception: Exception): ErrorCode {
    return when (exception) {
        is java.net.SocketTimeoutException -> ErrorCode.NETWORK_TIMEOUT
        is java.io.IOException -> ErrorCode.NETWORK_TIMEOUT
        is retrofit2.HttpException ->
            when(exception.code()) {
                400 -> ErrorCode.HTTP_CLIENT_BAD_REQUEST
                401 -> ErrorCode.HTTP_CLIENT_UNAUTHORIZED
                403 -> ErrorCode.HTTP_CLIENT_FORBIDDEN
                404 -> ErrorCode.HTTP_CLIENT_NOT_FOUND
                500 -> ErrorCode.HTTP_SERVER_ERROR
                501 -> ErrorCode.HTTP_SERVER_NOT_IMPLEMENTED
                502 -> ErrorCode.HTTP_SERVER_BAD_GATEWAY
                else -> ErrorCode.UNKNOWN_ERROR
            }
        else -> ErrorCode.UNKNOWN_ERROR
    }
}

//suspend fun <T> safeApiCall(call: suspend () -> T): T {
//    return try {
//        call()
//    } catch (e: HttpException) {
//        Log.d("0526safeApi", e.code().toString())
//        throw when (e.code()) {
//            400 -> ApiException.BadRequest
//            401 -> ApiException.Unauthorized
//            403 -> ApiException.Forbidden
//            404 -> ApiException.NotFound
//            in 500..599 -> ApiException.ServerError
//            else -> ApiException.Unknown
//        }
//    } catch (e: SocketTimeoutException) {
//        throw ApiException.TimeOut
//    } catch (e: UnknownHostException) {
//        throw ApiException.InternetError
//    } catch (e: Exception) {
//        throw ApiException.Unknown
//    }
//}