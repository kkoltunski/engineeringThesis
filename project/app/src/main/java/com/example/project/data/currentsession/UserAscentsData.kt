package com.example.project.data.currentsession

import com.example.project.database.DataBase.getConnection
import com.example.project.database.GradeMapper
import kotlin.math.roundToInt

class Route {
    var name: String = ""
    var grade: String = ""
}

class Ascent {
    var route: Route = Route()
    var style: String = ""
    var date: String = ""
}

class PercentageStyles {
    var onSight: Double = 0.0
    var flash: Double = 0.0
    var redPoint: Double = 0.0
}

object UserAscentsData {
    val ascents: MutableMap<String, MutableList<Ascent>> = mutableMapOf<String, MutableList<Ascent>>()

    fun synchronize() {
        fillAscentsFromDataBase()
    }

    private fun fillAscentsFromDataBase() {
        ascents.clear()

        val connection = getConnection()
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

            val ascentsForClimbingType = ascents.get(routeDataBase.getString("typeName"))
            if(ascentsForClimbingType != null) {
                ascentsForClimbingType.add(ascent)
            } else {
                val ascentList = mutableListOf<Ascent>()
                ascentList.add(ascent)
                ascents.put(routeDataBase.getString("typeName"), ascentList)
            }
        }
    }

    fun isStyleInAscents(typeName: String): Boolean {
        return if(ascents[typeName] != null) true else false
    }

    fun getStylesPercentage(typeName: String): PercentageStyles {
        val ascentsOfGivenType = ascents[typeName]!!

        val totalAscentsNumber: Double = ascentsOfGivenType.size.toDouble()
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
        percentageStyles.onSight = onSightAscentsNumber / totalAscentsNumber
        percentageStyles.flash = flashAscentsNumber / totalAscentsNumber
        percentageStyles.redPoint = redPointAscentsNumber / totalAscentsNumber

        return percentageStyles
    }

    fun getAverrageGrade(typeName: String): String {
        val ascentsOfGivenType = ascents[typeName]!!

        var sum = 0
        ascentsOfGivenType.forEach {
            sum += GradeMapper.nameToWeight(it.route.grade)!!
        }

        val averrageGrade = (sum / ascentsOfGivenType.size).toDouble()
        return GradeMapper.weightToName(averrageGrade.roundToInt())
    }
}