package com.example.project.ui.screens.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.project.ui.screens.models.userTypes.UserLogin
import com.example.project.ui.states.LoginAttemptStatus
import com.example.project.ui.states.LoginUiState
import com.example.project.ui.states.RegisterAttemptStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var userTypes by mutableStateOf(UserLogin())

    fun checkLoginAttempt() {
        if(!userTypes.isLoginValid()) {
            setLoginAttemptFailed(LoginAttemptStatus.BAD_CREDENTIALS)
        }

        if(!userTypes.isPasswordValid()) {
            setLoginAttemptFailed(LoginAttemptStatus.BAD_CREDENTIALS)
        }

        /*Add validation with DB*/
    }

    private fun setLoginAttemptFailed(loginAttemptStatus: LoginAttemptStatus) {
        _uiState.update { currentState ->
            currentState.copy(loginAttemptStatus = loginAttemptStatus)
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