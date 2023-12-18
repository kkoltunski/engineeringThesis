package com.example.project.ui.screens.chart

import androidx.compose.ui.graphics.Color
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarData
import com.example.project.data.Ascent

fun getAscentsChartData (
    ascents: List<Ascent>,
    primaryColor: Color,
    secondaryColor: Color,
    dataCategoryOptions: DataCategoryOptions = DataCategoryOptions()
): List<BarData> {
    val list = arrayListOf<BarData>()
    val gradeCountMap = getGradeCountMap(ascents)

    var index = 0f
    gradeCountMap.forEach { key, value ->
        val point = Point(index, value.toFloat())

        list.add(
            BarData(
                point = point,
                color = if(index.toInt().mod(2) == 0) primaryColor else secondaryColor,
                dataCategoryOptions = dataCategoryOptions,
                label = key,
            )
        )

        index = index.inc()
    }

    return list;
}

private fun getGradeCountMap(ascents: List<Ascent>): Map<String, Int>{
    val map = mutableMapOf<String, Int>()
    ascents.forEach {
        map.merge(it.route.grade, 1, Int::plus)
    }

    return map
}