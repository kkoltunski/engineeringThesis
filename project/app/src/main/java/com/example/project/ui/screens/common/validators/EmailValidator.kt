package com.example.project.ui.screens.common.validators

class EmailValidator : Validator<String> {
    override fun isValid(input: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }
}