package com.example.composegraphlibrary.barchart

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import com.example.composegraphlibrary.linegraph.data.GraphConstants
import kotlin.math.absoluteValue
import kotlin.math.ln
import kotlin.math.pow

data class BarChartValues(
    val listOfData: List<BarChartDataPoint>
) {
    data class BarChartDataPoint(
        val xLabel: String,
        val categories: List<Category>,
    )

    data class Category(val name: String, val value: Float, val color: Color)

    private val minMaxyValues: Pair<Float, Float>
        get() {
            var max = Float.MIN_VALUE
            var min = Float.MAX_VALUE

            listOfData.forEachIndexed { _, dataObject ->
                val maxCurrentCategoryValue = dataObject.categories.maxByOrNull { it.value }?.value?.absoluteValue ?: 0f
                val minCurrentCategoryValue = dataObject.categories.minByOrNull { it.value }?.value?.absoluteValue ?: 0f

                if (max < maxCurrentCategoryValue) {
                    max = maxCurrentCategoryValue
                }

                if (min > minCurrentCategoryValue) {
                    min = minCurrentCategoryValue
                }
            }

            return min to max
        }

    private val upperYValue: Float = minMaxyValues.second * (GraphConstants.DATASET_MAX_VALUE_PADDING)
    private val lowerYValue: Float = minMaxyValues.first * (GraphConstants.DATASET_MIN_VALUE_PADDING)

    val yLabelValues: List<String>
        get() {
            val yValues = mutableListOf<String>()
            repeat(GraphConstants.NUMBER_OF_Y_LABELS.toInt() + 1) {
                // val value = upperYValue * ((1f / NUMBER_OF_Y_LABELS) * (NUMBER_OF_Y_LABELS - it))
                val valueFromInterval = ((upperYValue - lowerYValue) * ((1f / GraphConstants.NUMBER_OF_Y_LABELS) * (GraphConstants.NUMBER_OF_Y_LABELS - it))) + lowerYValue
                yValues.add(getFormatedNumber(valueFromInterval.toLong()))
            }

            return yValues
        }

    fun getYPoint(quadrantRect: Rect, value: Float): Float {
        return (((upperYValue - value) / (upperYValue - lowerYValue))) * quadrantRect.height
    }

    // https://stackoverflow.com/questions/41859525/how-to-go-about-formatting-1200-to-1-2k-in-android-studio
    private fun getFormatedNumber(count: Long): String {
        if (count < 10000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }
}
