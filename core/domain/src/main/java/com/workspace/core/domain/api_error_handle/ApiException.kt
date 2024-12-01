package com.workspace.core.domain.api_error_handle

sealed class ApiException : Exception() {
    data object BadRequest : ApiException()
    data object Unauthorized : ApiException()
    data object Forbidden : ApiException()
    data object NotFound : ApiException()
    data object TimeOut : ApiException()
    data object ServerError : ApiException()
    data object InternetError : ApiException()
    data object Unknown : ApiException()
}