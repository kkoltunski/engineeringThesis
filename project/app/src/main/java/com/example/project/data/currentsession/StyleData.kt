package com.example.project.data.currentsession

import com.example.project.database.DataBase

object StyleData {
    var styles: MutableList<String> = arrayListOf()

    fun initialize() {
        val querry = "SELECT * FROM style"
        val result = DataBase.executeAndReturnQuerryResult(querry)

        while(result.next())
        {
            styles.add(result.getString(1))
        }
    }
}