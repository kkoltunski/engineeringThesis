package com.example.project.ui.screens.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.project.ui.screens.common.validators.LoginValidator
import com.example.project.ui.screens.common.validators.PasswordValidator
import com.example.project.ui.screens.models.userTypes.UserLogin
import com.example.project.ui.states.LoginAttemptStatus
import com.example.project.ui.states.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private val _loginValidator = LoginValidator()
    private val _passwordValidator = PasswordValidator()

    var userTypes by mutableStateOf(UserLogin())

    fun checkLoginAttempt() {
        if(!_loginValidator.isValid(userTypes.login)) {
            setLoginAttemptFailedWithBadCredentials()
        }

        if(!_passwordValidator.isValid(userTypes.password)) {
            setLoginAttemptFailedWithBadCredentials()
        }

        /*Add validation with DB*/
    }

    private fun setLoginAttemptFailedWithBadCredentials() {
        _uiState.update { currentState ->
            currentState.copy(loginAttemptStatus = LoginAttemptStatus.BAD_CREDENTIALS)
        }
    }

    fun resetView() {
        _uiState.value = LoginUiState(
            login = "",
            password = "",
            loginAttemptStatus = LoginAttemptStatus.OK
        )
    }

    init {
        resetView()
    }
}