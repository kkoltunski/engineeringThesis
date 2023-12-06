package com.example.project.ui.screens.models

import com.example.project.data.RegionData
import com.example.project.data.RockData
import com.example.project.data.RouteData
import com.example.project.data.currentsession.CurrentSessionData
import com.example.project.database.DataBase
import java.sql.ResultSet

class SearchViewModel : DataHarvester() {
    var regions: MutableList<RegionData> = mutableListOf<RegionData>()
    var rocks: MutableList<RockData> = mutableListOf<RockData>()
    var routes: MutableList<RouteData> = mutableListOf<RouteData>()

    override fun getDataFromDataBase() {
        if(CurrentSessionData.searchedPhrase.isNotEmpty()) {
            initializeRegionsData()
            initializeRocksData()
            initializeRoutesData()
        }
    }

    private fun initializeRegionsData() {
        regions.clear()
        var regionResultSet = prepareAndExecuteStatementForTable("region")

        while(regionResultSet.next()) {
            var region = RegionData()
            region.id = regionResultSet.getInt("id")
            region.name = regionResultSet.getString("name")

            regions.add(region)
        }
    }

    private fun initializeRocksData() {
        rocks.clear()
        var rocksResultSet = prepareAndExecuteStatementForTable("rock")

        while(rocksResultSet.next()) {
            var rock = RockData()
            rock.id = rocksResultSet.getInt("id")
            rock.name = rocksResultSet.getString("name")
            rock.regionName = getNameFromTableById("region", rocksResultSet.getInt("regionId"))

            rocks.add(rock)
        }
    }

    private fun initializeRoutesData() {
        routes.clear()
        var routeResultSet = prepareAndExecuteStatementForTable("route")

        while(routeResultSet.next()) {
            var route = RouteData()
            route.id = routeResultSet.getInt("id")
            route.name = routeResultSet.getString("name")

            val connection = DataBase.getConnection()
            val query = "SELECT name, regionId FROM rock WHERE id = ${routeResultSet.getString("rockId")}"
            val stmt = connection.prepareStatement(query)
            val rockResultSet = stmt.executeQuery()
            rockResultSet.next()

            route.rockName = rockResultSet.getString("name")
            route.regionName = getNameFromTableById("region", rockResultSet.getInt("regionId"))

            routes.add(route)
        }
    }

    private fun getNameFromTableById(tableName: String, id:Int ): String {
        val connection = DataBase.getConnection()
        val query = "SELECT name FROM $tableName WHERE id = $id"
        val stmt = connection.prepareStatement(query)
        val resultSet = stmt.executeQuery()
        resultSet.next()

        return resultSet.getString("name")
    }

    private fun prepareAndExecuteStatementForTable(tableName: String) : ResultSet {
        val connection = DataBase.getConnection()
        val query = "SELECT * FROM $tableName WHERE name LIKE '%$phrase%'"
        val stmt = connection.prepareStatement(query)
        return stmt.executeQuery()
    }
}