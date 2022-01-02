package com.example.composegraphlibrary.linegraph.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_MAX_VALUE_PADDING
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_MIN_VALUE_PADDING
import com.example.composegraphlibrary.linegraph.data.GraphConstants.NUMBER_OF_Y_LABELS

class LineGraphUtils(
    val listOfData: List<LineGraphDataPoint>
) {

    private val values: Pair<Float, Float>
        get() {
            val min = listOfData.minByOrNull { it.yValue }?.yValue ?: 0f
            val max = listOfData.maxByOrNull { it.yValue }?.yValue ?: 0f

            return min to max
        }

    private val lowerValue: Float = values.first * (DATASET_MIN_VALUE_PADDING)
    private val upperValue: Float = values.second * (DATASET_MAX_VALUE_PADDING)

    fun getDataPoints(quadrantRect: Rect): List<Pair<Offset, LineGraphDataPoint>> {
        val listOfDataPoints = mutableListOf<Pair<Offset, LineGraphDataPoint>>()
        listOfData.forEachIndexed { index, point ->
            val yCoordinate = (((upperValue - point.yValue) / (upperValue - lowerValue))) * quadrantRect.height
            val xCoordinate = (((quadrantRect.width / (listOfData.size - 1f) * (index))))
            listOfDataPoints.add(Pair(Offset(xCoordinate + quadrantRect.left, yCoordinate), point))
        }

        return listOfDataPoints
    }

    val yLabelValues: List<String>
        get() {
            return Utils.getYlabels(upperValue, lowerValue, NUMBER_OF_Y_LABELS)
        }
}
