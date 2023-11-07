package com.example.project.ui.screens.models.userTypes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.project.ui.screens.common.validators.EmailValidator
import com.example.project.ui.screens.common.validators.LoginValidator
import com.example.project.ui.screens.common.validators.PasswordValidator

interface UserTypes {
    val loginValidator: LoginValidator
    val passwordValidator: PasswordValidator

    fun updateLogin(typedUserLogin: String)
    fun updatePassword(typedUserPassword: String)
    fun isLoginValid() : Boolean
    fun isPasswordValid() : Boolean
}

open class UserLogin : UserTypes {
    override val loginValidator = LoginValidator()
    override val passwordValidator = PasswordValidator()

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

    override fun isLoginValid() : Boolean {
        return loginValidator.isValid(login)
    }

    override fun isPasswordValid() : Boolean {
        return passwordValidator.isValid(password)
    }
}

class UserRegister : UserLogin() {
    val emailValidator = EmailValidator()

    var passwordRepeat by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set

    fun updatePasswordRepeat(typedUserPasswordRepeat: String){
        passwordRepeat = typedUserPasswordRepeat
    }

    fun updateEmail(typedUserEmail: String){
        email = typedUserEmail
    }

    fun isEmailValid() : Boolean {
        return emailValidator.isValid(email)
    }
}
