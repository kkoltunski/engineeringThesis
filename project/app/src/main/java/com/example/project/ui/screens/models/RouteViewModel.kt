package com.example.project.ui.screens.models

import com.example.project.data.CommentData
import com.example.project.data.RouteData
import com.example.project.data.currentsession.PercentageStyles
import com.example.project.database.DataBase
import kotlin.math.roundToInt

class RouteViewModel: DataHarvester() {
    var routeData: RouteData = RouteData()
    var percentageStyles: PercentageStyles = PercentageStyles()
    var comments: MutableList<CommentData> = mutableListOf<CommentData>()
    var topology: MutableSet<String> = mutableSetOf<String>()

    override fun getDataFromDataBase() {
        initializeRouteData()
        initializePercentageStylesAndComments()
        initializeTopology()
    }

    private fun initializeRouteData() {
        routeData.id = phrase.toInt()

        val connection = DataBase.getConnection()
        var query = "SELECT * FROM route WHERE id = ${routeData.id}"
        var stmt = connection.prepareStatement(query)
        val routeResultSet = stmt.executeQuery()
        routeResultSet.next()

        routeData.name = routeResultSet.getString("name")
        routeData.grade = routeResultSet.getString("gradeName")
        routeData.type = routeResultSet.getString("typeName")

        query = "SELECT regionId, name FROM rock WHERE id = ${routeResultSet.getString("rockId")}"
        stmt = connection.prepareStatement(query)
        val rockResultSet = stmt.executeQuery()
        rockResultSet.next()

        routeData.rockName = rockResultSet.getString("name")

        query = "SELECT name FROM region WHERE id = ${rockResultSet.getString("regionId")}"
        stmt = connection.prepareStatement(query)
        val regionResultSet = stmt.executeQuery()
        regionResultSet.next()

        routeData.regionName = regionResultSet.getString("name")
    }

    private fun initializePercentageStylesAndComments() {
        val styles: MutableMap<String, Int> = mutableMapOf<String, Int>()

        val connection = DataBase.getConnection()
        val query = "SELECT date, styleName, comment FROM ascent WHERE routeId = ${routeData.id}"
        val stmt = connection.prepareStatement(query)
        val ascentsResultSet = stmt.executeQuery()

        while(ascentsResultSet.next()) {
            styles.merge(ascentsResultSet.getString("styleName"),1, Int::plus)

            val commentContent = ascentsResultSet.getString("comment")
            if(commentContent.isNotEmpty()) {
                val comment = CommentData()
                comment.date = ascentsResultSet.getString("date")
                comment.value = commentContent

                comments.add(comment)
            }
        }

        computePercentageStyles(styles.toMap())
    }

    private fun initializeTopology() {
        val connection = DataBase.getConnection()
        val query = "SELECT topologyName FROM routetopology WHERE routeId = ${routeData.id}"
        val stmt = connection.prepareStatement(query)
        val topologyResultSet = stmt.executeQuery()

        while(topologyResultSet.next()) {
            topology.add(topologyResultSet.getString("topologyName"))
        }
    }

    private fun computePercentageStyles(styles: Map<String, Int>) {
        var totalAscentsNumber = 0
        var onSightAscentsNumber = 0.0
        var flashAscentsNumber = 0.0
        var redPointAscentsNumber = 0.0

        styles.forEach {
            totalAscentsNumber += it.value
        }

        styles.forEach {
            when(it.key)
            {
                "OS" -> ++onSightAscentsNumber
                "FL" -> ++flashAscentsNumber
                "RP" -> ++redPointAscentsNumber
            }
        }

        if(totalAscentsNumber > 0) {
            percentageStyles.onSight = ((onSightAscentsNumber / totalAscentsNumber) * 100).roundToInt()
            percentageStyles.flash = ((flashAscentsNumber / totalAscentsNumber) * 100).roundToInt()
            percentageStyles.redPoint = ((redPointAscentsNumber / totalAscentsNumber) * 100).roundToInt()
        }
    }
}