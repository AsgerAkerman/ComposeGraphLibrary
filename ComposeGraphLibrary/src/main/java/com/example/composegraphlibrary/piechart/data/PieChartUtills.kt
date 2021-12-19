package com.example.composegraphlibrary.piechart.data

import androidx.compose.ui.graphics.Color

class PieChartUtills(
    val listOfSlices: List<Slice>
) {

    data class Slice(val value: Float, val label: String, val color: Color)

    private val totalSize: Float
        get() {
            var total = 0f
            listOfSlices.forEach { total += it.value }
            return total
        }

    fun calculateAngles(
        sliceSize: Float,
        progress: Float
    ): Float {
        return 360.0f * (sliceSize * progress) / totalSize
    }

    private val randomColor: Color
        get() {
            return Color((30..200).random(), (30..200).random(), (30..200).random())
        }
}
