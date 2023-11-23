package com.example.project.ui.screens.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.project.data.currentsession.UserAscentsData
import com.example.project.data.currentsession.UserData
import com.example.project.database.DataBase
import com.example.project.ui.screens.models.userTypes.UserLogin
import com.example.project.ui.states.LoginAttemptStatus
import com.example.project.ui.states.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.sql.ResultSet

class LoginViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var userTypes by mutableStateOf(UserLogin())

    fun checkLoginAttempt() {
        if(!userTypes.isLoginValid()) {
            setLoginAttemptStatus(LoginAttemptStatus.BAD_CREDENTIALS)
            return
        }

        if(!userTypes.isPasswordValid()) {
            setLoginAttemptStatus(LoginAttemptStatus.BAD_CREDENTIALS)
            return
        }

        val userDB = getUserFromDB()
        if(!userDB.next()) {
            setLoginAttemptStatus(LoginAttemptStatus.USER_DOES_NOT_EXIST)
            return
        }

        val passwordDB = userDB.getString(3)
        if(userTypes.password != passwordDB){
            setLoginAttemptStatus(LoginAttemptStatus.WRONG_PASSWORD)
            return
        }

        UserData.userId = userDB.getInt(1)
        setLoginAttemptStatus(LoginAttemptStatus.SUCCESS)
        UserAscentsData.synchronize()
    }

    fun getUserFromDB() : ResultSet {
        val querry = "SELECT * FROM user WHERE login = '${userTypes.login}'"
        return DataBase.executeAndReturnQuerryResult(querry)
    }

    private fun setLoginAttemptStatus(loginAttemptStatus: LoginAttemptStatus) {
        _uiState.update { currentState ->
            currentState.copy(loginAttemptStatus = loginAttemptStatus)
        }
    }

    fun resetView() {
        _uiState.value = LoginUiState(
            login = "",
            password = "",
            loginAttemptStatus = LoginAttemptStatus.UNKNOWN
        )
    }

    init {
        resetView()
    }
}