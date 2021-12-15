package com.example.composegraphlibrary.linegraph.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_LABEL_INTERVAL
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_MAX_VALUE_PADDING
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_MIN_VALUE_PADDING
import com.example.composegraphlibrary.linegraph.data.GraphConstants.NUMBER_OF_Y_LABELS

data class LineGraphValues(
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
        val dataInterval = (listOfData.size / DATASET_LABEL_INTERVAL)
        listOfData.forEachIndexed { index, point ->
            val yPointFromInterval = (((upperYValue - point.yValue) / (upperYValue - lowerYValue))) * quadrantRect.height
            val valueBetweenPoints = (((quadrantRect.width / DATASET_LABEL_INTERVAL) / dataInterval) * (index))
            listOfDataPoints.add(Pair(Offset(valueBetweenPoints + quadrantRect.left, yPointFromInterval), point))
        }

        return listOfDataPoints
    }
}
