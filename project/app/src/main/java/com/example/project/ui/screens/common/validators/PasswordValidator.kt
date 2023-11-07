package com.example.project.ui.screens.common.validators

import com.example.project.data.MAX_PASSWORD_SIZE
import com.example.project.data.MIN_PASSWORD_SIZE
import com.example.project.data.passwordRequiredSigns

class PasswordValidator : Validator<String> {
    override fun isValid(input: String): Boolean {
        when(input.length) {
            !in MIN_PASSWORD_SIZE..MAX_PASSWORD_SIZE -> return false
        }

        val upperCaseRegex = Regex("(.*[A-Z].*)")
        if(!input.matches(upperCaseRegex))
            return false

        var passwordRequiredSignPresent = false
        for(char in passwordRequiredSigns) {
            if(input.contains(char))
                passwordRequiredSignPresent = true
        }

        if(!passwordRequiredSignPresent) {
            return false
        }

        return true
    }
}