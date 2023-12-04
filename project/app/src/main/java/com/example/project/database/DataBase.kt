package com.example.project.database

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

object DataBase {
    @JvmStatic
    fun executeAndReturnQuerryResult(query: String): ResultSet {
        val connection = getConnection()
        val stmt: PreparedStatement = connection.prepareStatement(query)
        return stmt.executeQuery()
    }

    fun getConnection() : Connection {
        Class.forName("com.mysql.jdbc.Driver")

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val connectionString = "jdbc:mysql://192.168.1.12:3306/engineeringthesis" //my home
        val username = "root"
        val password = "root"

        return DriverManager.getConnection(connectionString, username, password)
    }
}

