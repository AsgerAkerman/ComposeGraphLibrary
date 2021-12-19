package com.example.composegraphlibrary.linegraph.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_MAX_VALUE_PADDING
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_MIN_VALUE_PADDING
import com.example.composegraphlibrary.linegraph.data.GraphConstants.NUMBER_OF_Y_LABELS

class LineGraphUtills(
    val listOfData: List<DataPoint>
) {
    data class DataPoint(val yValue: Float, val xLabel: String)

    private val yValues: Pair<Float, Float>
        get() {
            val min = listOfData.minByOrNull { it.yValue }?.yValue ?: 0f
            val max = listOfData.maxByOrNull { it.yValue }?.yValue ?: 0f

            return min to max
        }

    private val lowerYValue: Float = yValues.first * (DATASET_MIN_VALUE_PADDING)
    private val upperYValue: Float = yValues.second * (DATASET_MAX_VALUE_PADDING)

    val yLabelValues: List<String>
        get() {
            return Utils.getYlabels(upperYValue, lowerYValue, NUMBER_OF_Y_LABELS)
        }

    fun getDataPoints(quadrantRect: Rect): List<Pair<Offset, DataPoint>> {
        val listOfDataPoints = mutableListOf<Pair<Offset, DataPoint>>()
        listOfData.forEachIndexed { index, point ->
            val yPointFromInterval = (((upperYValue - point.yValue) / (upperYValue - lowerYValue))) * quadrantRect.height
            val valueBetweenPoints = (((quadrantRect.width / (listOfData.size - 1f) * (index))))
            listOfDataPoints.add(Pair(Offset(valueBetweenPoints + quadrantRect.left, yPointFromInterval), point))
        }

        return listOfDataPoints
    }
}
