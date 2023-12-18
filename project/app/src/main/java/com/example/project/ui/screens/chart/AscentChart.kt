package com.example.project.ui.screens.chart

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle

@Composable
fun AscentChart(
    chartData: List<BarData>,
    modifier: Modifier = Modifier
) {
    val yStepSize = chartData.maxWith(Comparator.comparingInt {it.point.y.toInt()}).point.y.toInt()

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(chartData.size - 1)
        .bottomPadding(12.dp)
        .axisLabelAngle(10f)
        .startDrawPadding(25.dp)
        .shouldDrawAxisLineTillEnd(false)
        .labelData { index -> chartData[index].label }
        .build()
    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> index.toString() }
        .build()

    val barChartData = BarChartData(
        chartData = chartData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        barStyle = BarStyle(
            paddingBetweenBars = 20.dp,
            barWidth = 20.dp
        ),
        backgroundColor = Color.White.copy(alpha = 0.0f),
        showYAxis = true,
        showXAxis = true,
        horizontalExtraSpace = 10.dp,
        drawBar = { drawScope, barData, drawOffset, height, barChartType, barStyle ->
            with(drawScope) {
                with(barStyle) {
                    drawRect(
                        color = barData.color,
                        topLeft = drawOffset,
                        size = Size(barWidth.toPx(), height),
                        style = barDrawStyle,
                        blendMode = barBlendMode
                    )
                }
            }
        }
    )

    BarChart(
        modifier = modifier
            .height(200.dp),
        barChartData = barChartData
    )
}