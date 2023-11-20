package com.example.project.ui.states

enum class AscentViewStatus {
    UNKNOWN,
    ROUTE_NOT_FOUND,
    ROUTE_FOUND,
    ASCENT_SAVED
}

data class AscentUiState(
    val ascentViewStatus: AscentViewStatus = AscentViewStatus.UNKNOWN
)
