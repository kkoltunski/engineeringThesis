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
    var rockName: String? = null
    var regionName: String? = null
    var id: Int = 0
    var name: String = ""
    var grade: String? = null
    var type: String? = null
    var boltsNumber: Int? = null
}

class GradeData {
    var name: String = ""
    var value: Int = 0
}

class CommentData {
    var date: String = ""
    var value: String = ""
}

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
    var onSight: Int = 0
    var flash: Int = 0
    var redPoint: Int = 0
}