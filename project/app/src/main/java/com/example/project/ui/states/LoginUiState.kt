package com.example.project.ui.states

enum class LoginAttemptStatus {
    UNKNOWN,
    BAD_CREDENTIALS,
    USER_DOES_NOT_EXIST,
    WRONG_PASSWORD,
    SUCCESS
}

data class LoginUiState(
    val login: String = "",
    val password: String = "",
    val loginAttemptStatus: LoginAttemptStatus = LoginAttemptStatus.UNKNOWN
)
