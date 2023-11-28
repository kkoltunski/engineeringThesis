package com.example.project.ui.screens.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import co.yml.charts.ui.barchart.models.BarData
import com.example.project.data.*
import com.example.project.data.currentsession.Ascent
import com.example.project.data.currentsession.PercentageStyles
import com.example.project.data.currentsession.UserAscentsData
import com.example.project.ui.screens.chart.getAscentsChartData
import com.example.project.ui.states.ClimbingType
import com.example.project.ui.states.PersonalUiState
import com.example.project.ui.states.TimeInterval
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BadgesData {
    var totalAscents by mutableStateOf(0)
    var avgGrade by mutableStateOf("")
    var percentageStyles by mutableStateOf(PercentageStyles())
}

class PersonalViewModel() : ViewModel() {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private var primaryColor: Color = Color(0.7372549f, 0.078431375f, 0.03529412f, 1.0f)
    private var secondaryColor: Color = Color(0.0f, 0.40784314f, 0.45490196f, 1.0f)

    private val _uiState = MutableStateFlow(PersonalUiState())
    val uiState: StateFlow<PersonalUiState> = _uiState.asStateFlow()

    var isClimbingTypeDataPresent by mutableStateOf(false)
    var badgesData by mutableStateOf(BadgesData())
    var selectedClimbingType by mutableStateOf(CLIMBING_TYPE_SPORT)

    var selectedTimeInterval by mutableStateOf(TIME_INTERVAL_ALL)
    var presentDate = LocalDate.now().format(formatter)

    var ascents by mutableStateOf(listOf<Ascent>())
    var chartData by mutableStateOf(listOf<BarData>())

    fun changeSelectedClimbingType(climbingType: String) {
        selectedClimbingType = climbingType

        when(climbingType) {
            CLIMBING_TYPE_SPORT -> setClimbingType(ClimbingType.SPORT)
            CLIMBING_TYPE_TRAD -> setClimbingType(ClimbingType.TRAD)
            CLIMBING_TYPE_BOULDERS -> setClimbingType(ClimbingType.BOULDER)
        }

        reloadBadgesData()
        getAscentsAndChartData()
    }

    fun changeSelectedTimeInterval(timeInterval: String) {
        selectedTimeInterval = timeInterval

        when(timeInterval) {
            TIME_INTERVAL_ALL -> setTimeInterval(TimeInterval.ALL)
            TIME_INTERVAL_3M -> setTimeInterval(TimeInterval._3M)
            TIME_INTERVAL_6M -> setTimeInterval(TimeInterval._6M)
            TIME_INTERVAL_12M -> setTimeInterval(TimeInterval._12M)
        }

        getAscentsAndChartData()
    }

    private fun reloadBadgesData() {
        val typeName = mapSelectedClimbingTypeToDataBaseName()
        isClimbingTypeDataPresent = UserAscentsData.isStyleInAscents(typeName)

        if(isClimbingTypeDataPresent) {
            badgesData.totalAscents = UserAscentsData.getTypeAscentsNumber(typeName)
            badgesData.avgGrade = UserAscentsData.getAverrageGrade(typeName)
            badgesData.percentageStyles = UserAscentsData.getStylesPercentage(typeName)
        }
    }

    private fun getAscentsData() {
        ascents = UserAscentsData.getTypeAscents(mapSelectedClimbingTypeToDataBaseName())

        val monthsToSubstract: Long = when(selectedTimeInterval) {
            TIME_INTERVAL_3M -> 3
            TIME_INTERVAL_6M -> 6
            TIME_INTERVAL_12M -> 12
            else -> 0
        }

        if(monthsToSubstract > 0) {
            val pastBeginDate = LocalDate.parse(presentDate, formatter).minusMonths(monthsToSubstract).toString()

            ascents = ascents.mapNotNull { ascent ->
                if(ascent.date >= pastBeginDate ) {
                    ascent
                } else {
                    null
                }
            }
        }

        ascents = ascents.sortedByDescending { it.date }
    }

    private fun getChartData() {
        chartData = getAscentsChartData(
            ascents.sortedBy { it.route.grade },
            primaryColor,
            secondaryColor
        )
    }

    private fun mapSelectedClimbingTypeToDataBaseName(): String {
        return when(selectedClimbingType)
        {
            CLIMBING_TYPE_SPORT -> "sport"
            CLIMBING_TYPE_TRAD -> "trad"
            CLIMBING_TYPE_BOULDERS -> "bouldering"
            else -> ""
        }
    }

    private fun setClimbingType(climbingType: ClimbingType) {
        _uiState.update { currentState ->
            currentState.copy(climbingType = climbingType)
        }
    }

    private fun setTimeInterval(timeInterval: TimeInterval) {
        _uiState.update { currentState ->
            currentState.copy(timeInterval = timeInterval)
        }
    }

    fun resetView() {
        _uiState.value = PersonalUiState(
            climbingType = ClimbingType.SPORT,
            timeInterval = TimeInterval.ALL
        )
    }

    private fun getAscentsAndChartData() {
        if(isClimbingTypeDataPresent) {
            getAscentsData()
            getChartData()
        }
    }

    init {
        resetView()
        reloadBadgesData()
        getAscentsAndChartData()
    }
}