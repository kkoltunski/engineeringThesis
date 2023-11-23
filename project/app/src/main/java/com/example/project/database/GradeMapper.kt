package com.example.project.database

object GradeMapper {
    private val map: MutableMap<Int, String> = mutableMapOf<Int, String>()

    fun initialize() {
        val querry = "SELECT * FROM grade ORDER BY name ASC"
        val result = DataBase.executeAndReturnQuerryResult(querry)

        var keyCounter = 0
        while(result.next())
        {
            map.put(keyCounter, result.getString("name"))
            ++keyCounter
        }
    }

    fun nameToWeight(name: String): Int? {
        for (key in map.keys)
        {
            if (name == map[key]) {
                return key
            }
        }

        return null
    }

    fun weightToName(key: Int): String {
        return map[key]!!
    }
}