package com.example.composegraphlibrary.barchart.data

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.linegraph.data.GraphConstants
import com.example.composegraphlibrary.linegraph.data.GraphConstants.NUMBER_OF_Y_LABELS
import kotlin.math.absoluteValue

class BarChartUtils(
    val listOfData: List<BarChartDataPoint>
) {

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
            return Utils.getYlabels(upperYValue, lowerYValue, NUMBER_OF_Y_LABELS)
        }

    fun getYPoint(quadrantRect: Rect, value: Float): Float {
        return (((upperYValue - value) / (upperYValue - lowerYValue))) * quadrantRect.height
    }
}
