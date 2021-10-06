package com.example.composetestproject.ui.theme

data class LineGraphValues(
    val listOfData: List<Point>,
    var valuePadding: Float = 0.3f
) {

    private val yMinMax: Pair<Float, Float>
        get() {
            val min = listOfData.minByOrNull { it.yValue }?.yValue ?: 0f
            val max = listOfData.maxByOrNull { it.yValue }?.yValue ?: 0f

            return min to max
        }

    val maxYValue: Float = yMinMax.second * (valuePadding + 1)

    val minYValue: Float = yMinMax.first * (1 - valuePadding)

    data class Point(val yValue: Float, val xLabel: String)
}
