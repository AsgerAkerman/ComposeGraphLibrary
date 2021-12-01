package com.example.composegraphlibrary.piechart

import androidx.compose.ui.graphics.Color

data class PieChartValues(
    private val listOfDataPoints: List<Pair<Float, String>>
) {
    private val listOfPieData = mutableListOf<Slice>()

    init {
        listOfDataPoints.forEach { data ->
            listOfPieData.add(Slice(data.first, data.second, randomColor))
        }
    }
    data class Slice(val value: Float, val label: String, val color: Color)

    private val totalSize: Float
        get() {
            var total = 0f
            listOfPieData.forEach { total += it.value }
            return total
        }

    val pieSlices: List<Pair<Slice, Float>>
        get() {
            val listOfSlicesWithAngle = mutableListOf<Pair<Slice, Float>>()
            listOfPieData.forEachIndexed { _, index ->
                listOfSlicesWithAngle.add(Pair(index, calculateAngles(index.value, totalSize)))
            }
            return listOfSlicesWithAngle
        }

    private fun calculateAngles(
        sliceSize: Float,
        totalSize: Float,
        // progress: Float
    ): Float {
        return 360.0f * (sliceSize) / totalSize
    }

    private val randomColor: Color
        get() {
            return Color((30..200).random(), (30..200).random(), (30..200).random())
        }
}
