package com.example.project.data

class RegionData{
    var id: Int = 0
    var name: String = ""
}

class RockData {
    var regionName: String? = null
    var id: Int = 0
    var name: String = ""
}

class RouteData {
    var rockName: String = ""
    var regionName: String = ""
    var id: Int = 0
    var name: String = ""
    var grade: String = ""
}

class GradeData {
    var name: String = ""
    var value: Int = 0
}