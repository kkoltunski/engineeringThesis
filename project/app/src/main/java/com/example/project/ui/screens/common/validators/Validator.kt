package com.example.project.ui.screens.common.validators

interface Validator<T> {
    abstract fun isValid(input: T): Boolean
}