package com.example.composegraphlibrary.linegraph.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_LABEL_INTERVAL
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_MAX_VALUE_PADDING
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_MIN_VALUE_PADDING
import com.example.composegraphlibrary.linegraph.data.GraphConstants.NUMBER_OF_Y_LABELS
import kotlin.math.ln
import kotlin.math.pow

data class LineGraphValues(
    val listOfData: List<DataPoint>
) {
    data class DataPoint(val yValue: Float, val xLabel: String)

    private val yValues: Pair<Float, Float>
        get() {
            val max = listOfData.maxByOrNull { it.yValue }?.yValue ?: 0f
            val min = listOfData.minByOrNull { it.yValue }?.yValue ?: 0f
            return min to max
        }

    private val upperYValue: Float = yValues.second * (DATASET_MAX_VALUE_PADDING)
    private val lowerYValue: Float = yValues.first * (DATASET_MIN_VALUE_PADDING)

    val yLabelValues: List<String>
        get() {
            val yValues = mutableListOf<String>()
            repeat(NUMBER_OF_Y_LABELS.toInt() + 1) {
               // val value = upperYValue * ((1f / NUMBER_OF_Y_LABELS) * (NUMBER_OF_Y_LABELS - it))
                val valueFromInterval = ((upperYValue - lowerYValue) * ((1f / NUMBER_OF_Y_LABELS) * (NUMBER_OF_Y_LABELS - it))) + lowerYValue
                yValues.add(getFormatedNumber(valueFromInterval.toLong()))
            }

            return yValues
        }

    fun getDataPoints(quadrantRect: Rect): List<Pair<Offset, DataPoint>> {
        val listOfDataPoints = mutableListOf<Pair<Offset, DataPoint>>()
        val dataInterval = (listOfData.size / DATASET_LABEL_INTERVAL)
        listOfData.forEachIndexed { index, point ->
      //    val yPoint = ((1f - (point.yValue / upperYValue)) * quadrantRect.height)
            val yPointFromInterval = (((upperYValue - point.yValue) / (upperYValue - lowerYValue))) * quadrantRect.height
            val valueBetweenPoints = (((quadrantRect.width / DATASET_LABEL_INTERVAL) / dataInterval) * (index))
            listOfDataPoints.add(Pair(Offset(valueBetweenPoints + quadrantRect.left, yPointFromInterval), point))
        }

        return listOfDataPoints
    }

    // https://stackoverflow.com/questions/41859525/how-to-go-about-formatting-1200-to-1-2k-in-android-studio
    private fun getFormatedNumber(count: Long): String {
        if (count < 10000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }
}
