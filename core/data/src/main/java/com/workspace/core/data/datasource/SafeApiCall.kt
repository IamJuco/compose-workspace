package com.workspace.core.data.datasource

import com.workspace.core.domain.api_error_handle.ApiException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(call: suspend () -> T): T {
    return try {
        call()
    } catch (e: HttpException) {
        throw when (e.code()) {
            400 -> ApiException.BadRequest
            401 -> ApiException.Unauthorized
            403 -> ApiException.Forbidden
            404 -> ApiException.NotFound
            in 500..599 -> ApiException.ServerError
            else -> ApiException.Unknown
        }
    } catch (e: SocketTimeoutException) {
        throw ApiException.TimeOut
    } catch (e: UnknownHostException) {
        throw ApiException.InternetError
    } catch (e: Exception) {
        throw ApiException.Unknown
    }
}