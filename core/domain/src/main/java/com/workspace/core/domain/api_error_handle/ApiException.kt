package com.workspace.core.domain.api_error_handle

sealed class ApiException : Exception() {
    data object BadRequest : ApiException() {
        private fun readResolve(): Any = BadRequest
    }

    data object Unauthorized : ApiException() {
        private fun readResolve(): Any = Unauthorized
    }

    data object Forbidden : ApiException() {
        private fun readResolve(): Any = Forbidden
    }

    data object NotFound : ApiException() {
        private fun readResolve(): Any = NotFound
    }

    data object TimeOut : ApiException() {
        private fun readResolve(): Any = TimeOut
    }

    data object ServerError : ApiException() {
        private fun readResolve(): Any = ServerError
    }

    data object InternetError : ApiException() {
        private fun readResolve(): Any = InternetError
    }

    data object Unknown : ApiException() {
        private fun readResolve(): Any = Unknown
    }
}