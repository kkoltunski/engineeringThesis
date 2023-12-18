package com.example.project.ui.screens.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import co.yml.charts.ui.barchart.models.BarData
import com.example.project.data.*
import com.example.project.database.DataBase
import com.example.project.database.GradeMapper
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
import kotlin.math.roundToInt

class BadgesData {
    var totalAscents by mutableStateOf(0)
    var avgGrade by mutableStateOf("")
    var percentageStyles by mutableStateOf(PercentageStyles())
}

class PersonalViewModel() : DataHarvester() {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private var primaryColor: Color = Color(0.7372549f, 0.078431375f, 0.03529412f, 1.0f)
    private var secondaryColor: Color = Color(0.0f, 0.40784314f, 0.45490196f, 1.0f)

    private val _personalUiState = MutableStateFlow(PersonalUiState())
    val personalUiState: StateFlow<PersonalUiState> = _personalUiState.asStateFlow()

    var isClimbingTypeDataPresent by mutableStateOf(false)
    var badgesData by mutableStateOf(BadgesData())
    var selectedClimbingType by mutableStateOf(CLIMBING_TYPE_SPORT)

    var selectedTimeInterval by mutableStateOf(TIME_INTERVAL_ALL)
    var presentDate = LocalDate.now().format(formatter)

    val allAscents: MutableMap<String, MutableList<Ascent>> = mutableMapOf<String, MutableList<Ascent>>()
    var ascents by mutableStateOf(listOf<Ascent>())
    var chartData by mutableStateOf(listOf<BarData>())

    override fun getDataFromDataBase() {
        resetView()
        getAscentsFromDataBase()
        reloadBadgesData()
        getAscentsAndChartData()
    }

    private fun getAscentsFromDataBase() {
        allAscents.clear()

        val connection = DataBase.getConnection()
        var query = "SELECT * FROM ascent"
        var stmt = connection.prepareStatement(query)
        val ascentsDataBase = stmt.executeQuery()

        while(ascentsDataBase.next()) {
            val routeId = ascentsDataBase.getInt("routeId")
            query = "SELECT name, gradeName, typeName FROM route WHERE id = $routeId"
            stmt = connection.prepareStatement(query)
            val routeDataBase = stmt.executeQuery()
            routeDataBase.next()

            val ascent = Ascent()
            ascent.route.name = routeDataBase.getString("name")
            ascent.route.grade = routeDataBase.getString("gradeName")
            ascent.style = ascentsDataBase.getString("styleName")
            ascent.date = ascentsDataBase.getString("date")

            val ascentsForClimbingType = allAscents.get(routeDataBase.getString("typeName"))
            if(ascentsForClimbingType != null) {
                ascentsForClimbingType.add(ascent)
            } else {
                val ascentList = mutableListOf<Ascent>()
                ascentList.add(ascent)
                allAscents.put(routeDataBase.getString("typeName"), ascentList)
            }
        }
    }

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
        isClimbingTypeDataPresent = allAscents[typeName] != null

        if(isClimbingTypeDataPresent) {
            badgesData.totalAscents = allAscents[typeName]!!.size
            badgesData.avgGrade = getAverrageGrade(typeName)
            badgesData.percentageStyles = getStylesPercentage(typeName)
        }
    }

    fun getAverrageGrade(typeName: String): String {
        val ascentsOfGivenType = allAscents[typeName]!!

        var sum = 0
        ascentsOfGivenType.forEach {
            sum += GradeMapper.nameToWeight(it.route.grade)!!
        }

        val averrageGrade = (sum / ascentsOfGivenType.size).toDouble()
        return GradeMapper.weightToName(averrageGrade.roundToInt())
    }

    fun getStylesPercentage(typeName: String): PercentageStyles {
        val ascentsOfGivenType = allAscents[typeName]!!

        val totalAscentsNumber = ascentsOfGivenType.size.toDouble()
        var onSightAscentsNumber = 0.0
        var flashAscentsNumber = 0.0
        var redPointAscentsNumber = 0.0

        ascentsOfGivenType.forEach {
            val style = it.style
            when(style)
            {
                "OS" -> ++onSightAscentsNumber
                "FL" -> ++flashAscentsNumber
                "RP" -> ++redPointAscentsNumber
            }
        }

        val percentageStyles = PercentageStyles()
        percentageStyles.onSight = ((onSightAscentsNumber / totalAscentsNumber) * 100).roundToInt()
        percentageStyles.flash = ((flashAscentsNumber / totalAscentsNumber) * 100).roundToInt()
        percentageStyles.redPoint = ((redPointAscentsNumber / totalAscentsNumber) * 100).roundToInt()

        return percentageStyles
    }

    private fun getAscentsData() {
        val typeName = mapSelectedClimbingTypeToDataBaseName()
        ascents = allAscents[typeName]!!

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
        _personalUiState.update { currentState ->
            currentState.copy(climbingType = climbingType)
        }
    }

    private fun setTimeInterval(timeInterval: TimeInterval) {
        _personalUiState.update { currentState ->
            currentState.copy(timeInterval = timeInterval)
        }
    }

    private fun resetView() {
        _personalUiState.value = PersonalUiState(
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
}