package com.example.project.ui.screens.models.userTypes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

interface UserTypes {
    abstract fun updateLogin(typedUserLogin: String)
    abstract fun updatePassword(typedUserPassword: String)
}

class UserLogin : UserTypes {
    var login by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    override fun updateLogin(typedUserLogin: String){
        login = typedUserLogin
    }

    override fun updatePassword(typedUserPassword: String){
        password = typedUserPassword
    }
}
