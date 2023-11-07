package com.example.project.ui.screens.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.project.ui.screens.models.userTypes.UserRegister
import com.example.project.ui.states.RegisterAttemptStatus
import com.example.project.ui.states.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    var userTypes by mutableStateOf(UserRegister())

    fun checkRegisterAttempt() {
        if(!userTypes.isLoginValid()) {
            setRegisterAttemptFailed(RegisterAttemptStatus.BAD_CREDENTIALS)
            return
        }

        if(!userTypes.isPasswordValid()) {
            setRegisterAttemptFailed(RegisterAttemptStatus.BAD_CREDENTIALS)
            return
        }

        if(!userTypes.password.equals(userTypes.passwordRepeat)) {
            setRegisterAttemptFailed(RegisterAttemptStatus.PASSWORDS_DO_NOT_MATCH)
            return
        }

        if(!userTypes.isEmailValid()) {
            setRegisterAttemptFailed(RegisterAttemptStatus.WRONG_EMAIL)
            return
        }

        /*Add validation with DB*/
    }

    private fun setRegisterAttemptFailed(registerAttemptStatus: RegisterAttemptStatus) {
        _uiState.update { currentState ->
            currentState.copy(registerAttemptStatus = registerAttemptStatus)
        }
    }

    fun resetView() {
        _uiState.value = RegisterUiState(
            login = "",
            password = "",
            passwordRepeat = "",
            email = "",
            registerAttemptStatus = RegisterAttemptStatus.OK
        )
    }

    init {
        resetView()
    }
}