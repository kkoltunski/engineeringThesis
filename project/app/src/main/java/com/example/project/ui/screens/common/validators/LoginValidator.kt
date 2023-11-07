package com.example.project.ui.screens.common.validators

import com.example.project.data.MAX_LOGIN_SIZE
import com.example.project.data.MIN_LOGIN_SIZE
import com.example.project.data.loginForbiddenSigns

class LoginValidator : Validator<String> {
    override fun isValid(input: String): Boolean {
        when(input.length) {
            !in MIN_LOGIN_SIZE..MAX_LOGIN_SIZE -> return false
        }

        loginForbiddenSigns.forEach{ if(input.contains(it)) return false }

        if(input.contains(".."))
            return false

        if(input.startsWith('.') or input.endsWith('.'))
            return false

        return true
    }
}