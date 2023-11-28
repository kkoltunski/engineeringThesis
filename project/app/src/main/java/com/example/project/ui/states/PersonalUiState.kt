package com.example.project.ui.states

enum class ClimbingType() {
    SPORT,
    TRAD,
    BOULDER
}

enum class TimeInterval() {
    ALL,
    _3M,
    _6M,
    _12M,
}

data class PersonalUiState (
    val climbingType: ClimbingType = ClimbingType.SPORT,
    val timeInterval: TimeInterval = TimeInterval.ALL
)
