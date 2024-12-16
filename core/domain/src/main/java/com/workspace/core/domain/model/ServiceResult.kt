package com.workspace.core.domain.model

sealed class ServiceResult<out T> {
    data class Success<T>(val data: T) : ServiceResult<T>()
    data class Error(val errorCode: ErrorCode, val errorMessage: String? = null) :
        ServiceResult<Nothing>()
}

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

enum class FirebaseAuthError(val message: String) {
    INVALID_EMAIL("유효하지 않은 이메일 형식입니다."),
    USER_DISABLED("사용이 중지된 계정입니다."),
    USER_NOT_FOUND("계정을 찾을 수 없습니다."),
    WRONG_PASSWORD("비밀번호가 잘못되었습니다."),
    EMAIL_ALREADY_IN_USE("이미 사용 중인 이메일입니다."),
    OPERATION_NOT_ALLOWED("허용되지 않은 요청입니다."),
    WEAK_PASSWORD("비밀번호가 너무 약합니다."),
    INVALID_CREDENTIAL("잘못된 자격 증명입니다."),
    ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL("다른 인증 방식으로 이미 등록된 계정입니다."),
    CREDENTIAL_ALREADY_IN_USE("이미 사용 중인 자격 증명입니다."),
    REQUIRES_RECENT_LOGIN("이 작업을 수행하려면 최근 로그인이 필요합니다."),
    TOO_MANY_REQUESTS("요청이 너무 많습니다. 나중에 다시 시도하세요."),
    PROVIDER_ALREADY_LINKED("인증 제공자가 이미 연결되었습니다."),
    NO_SUCH_PROVIDER("해당 제공자를 찾을 수 없습니다."),
    INVALID_USER_TOKEN("잘못된 사용자 토큰입니다."),
    USER_TOKEN_EXPIRED("사용자 토큰이 만료되었습니다."),
    NETWORK_REQUEST_FAILED("네트워크 요청에 실패했습니다. 인터넷 연결을 확인하세요."),
    INTERNAL_ERROR("서버 내부 오류가 발생했습니다."),
    UNKNOWN_ERROR("알 수 없는 오류가 발생했습니다.")
}