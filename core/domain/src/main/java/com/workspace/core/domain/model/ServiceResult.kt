package com.workspace.core.domain.model

sealed class ServiceResult<out R> {
    data object Loading : ServiceResult<Nothing>()
    data class Success<out T>(val data: T) : ServiceResult<T>()
    data class Error(val error: ErrorCode) : ServiceResult<Nothing>()
}
val ServiceResult<*>.succeeded
    get() = this is ServiceResult.Success && data != null


enum class ErrorCode(val message: String) {
    HTTP_CLIENT_BAD_REQUEST("잘못된 요청입니다."),
    HTTP_CLIENT_UNAUTHORIZED("권한이 없습니다. 로그인 후 다시 시도해주세요."),
    HTTP_CLIENT_FORBIDDEN("접근이 금지되었습니다."),
    HTTP_CLIENT_NOT_FOUND("요청한 리소스를 찾을 수 없습니다."),
    HTTP_SERVER_ERROR("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    HTTP_SERVER_NOT_IMPLEMENTED("요청한 기능이 지원되지 않습니다."),
    HTTP_SERVER_BAD_GATEWAY("서버 간 통신 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    NETWORK_TIMEOUT("네트워크 요청에 실패했습니다. 인터넷 연결을 확인해 주세요."),
    UNKNOWN_ERROR("알 수 없는 오류가 발생했습니다.")
}

//sealed interface UiState<out T> {
//    data object Loading : UiState<Nothing>
//    data class Success<T>(val data: T) : UiState<T>
//    data class Failure(val exception: ApiException) : UiState<Nothing>
//}
//
//sealed class AppError(val message: String) {
//    object NetworkTimeout : AppError("네트워크 시간이 초과되었습니다. 다시 시도해주세요.")
//    object NetworkError : AppError("네트워크 연결에 문제가 있습니다. 인터넷을 확인해주세요.")
//    object NotFound : AppError("데이터를 찾을 수 없습니다.")
//    object ServerError : AppError("서버에 문제가 발생했습니다. 나중에 다시 시도해주세요.")
//    object ApiError : AppError("알 수 없는 API 오류가 발생했습니다.")
//    object UnknownError : AppError("알 수 없는 오류가 발생했습니다.")
//}
