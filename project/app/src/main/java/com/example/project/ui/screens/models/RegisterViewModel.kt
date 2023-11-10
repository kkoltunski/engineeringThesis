package com.example.project.ui.screens.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.project.database.DataBase
import com.example.project.database.DataBase.getConnection
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
            setRegisterAttemptStatus(RegisterAttemptStatus.BAD_CREDENTIALS)
            return
        }

        if(!userTypes.isPasswordValid()) {
            setRegisterAttemptStatus(RegisterAttemptStatus.BAD_CREDENTIALS)
            return
        }

        if(!userTypes.password.equals(userTypes.passwordRepeat)) {
            setRegisterAttemptStatus(RegisterAttemptStatus.PASSWORDS_DO_NOT_MATCH)
            return
        }

        if(!userTypes.isEmailValid()) {
            setRegisterAttemptStatus(RegisterAttemptStatus.WRONG_EMAIL)
            return
        }

        if(doesLoginExistInDataBase())
        {
            setRegisterAttemptStatus(RegisterAttemptStatus.USER_EXIST)
            return
        }

        if(doesEmailExistInDataBase())
        {
            setRegisterAttemptStatus(RegisterAttemptStatus.EMAIL_IN_USE)
            return
        }

        if(putUserIntoDataBase() > 0) {
            setRegisterAttemptStatus(RegisterAttemptStatus.SUCCESS)
        }
    }

    fun putUserIntoDataBase() : Int {
        val querry = "INSERT INTO user(login, password, email) VALUES (?, ?, ?)"
        val statement = getConnection().prepareStatement(querry)
        statement.setString(1, userTypes.login)
        statement.setString(2, userTypes.password)
        statement.setString(3, userTypes.email)

        return statement.executeUpdate()
    }

    fun doesLoginExistInDataBase() : Boolean {
        val querry = "SELECT * FROM user WHERE login = '${userTypes.login}'"
        val result = DataBase.executeAndReturnQuerryResult(querry)

        return result.next()
    }

    fun doesEmailExistInDataBase() : Boolean {
        val querry = "SELECT * FROM user WHERE email = '${userTypes.email}'"
        val result = DataBase.executeAndReturnQuerryResult(querry)

        return result.next()
    }

    private fun setRegisterAttemptStatus(registerAttemptStatus: RegisterAttemptStatus) {
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
            registerAttemptStatus = RegisterAttemptStatus.UNKNOWN
        )
    }

    init {
        resetView()
    }
}