package com.example.project.data.currentsession

import com.example.project.database.DataBase

object ClimbingTypeData {
    var types: MutableList<String> = arrayListOf()

    fun initialize() {
        val querry = "SELECT * FROM type"
        val result = DataBase.executeAndReturnQuerryResult(querry)

        while(result.next())
        {
            types.add(result.getString(1))
        }
    }
}