package com.example.project.ui.states

enum class RegisterAttemptStatus {
    UNKNOWN,
    BAD_CREDENTIALS,
    PASSWORDS_DO_NOT_MATCH,
    WRONG_EMAIL,
    USER_EXIST,
    EMAIL_IN_USE,
    SUCCESS
}

data class RegisterUiState(
    val login: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val email: String = "",
    val registerAttemptStatus: RegisterAttemptStatus = RegisterAttemptStatus.UNKNOWN
)
