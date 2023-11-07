package com.example.project.ui.screens.models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import com.example.project.ui.screens.common.validators.LoginValidator
import com.example.project.ui.screens.common.validators.PasswordValidator
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
    var userLogin by mutableStateOf("")
        private set
    var userPassword by mutableStateOf("")
        private set

    fun updateUserLogin(typedUserLogin: String){
        userLogin = typedUserLogin
    }

    fun updateUserPassword(typedUserPassword: String){
        userPassword = typedUserPassword
    }

    fun checkLoginAttempt() {
        if(!_loginValidator.isValid(userLogin)) {
            setLoginAttemptFailedWithBadCredentials()
        }

        if(!_passwordValidator.isValid(userPassword)) {
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