package com.example.composegraphlibrary.piechart.data

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Paint

object PieChartUtils {

    private fun getTotalValue(data: List<Slice>): Float {
        var total = 0f
        data.forEach { total += it.value }
        return total
    }

    private fun calculateAngles(
        sliceSize: Float,
        progress: Float,
        data: List<Slice>
    ): Float {
        return 360.0f * (sliceSize * progress) / getTotalValue(data)
    }

    fun getPieChartData(
        data: List<Slice>,
        drawablePieRect: Rect,
        progress: Float
    ): PieChartSlicesData {
        var startAngle = 0f
        val tempData = mutableListOf<PieChartSliceData>()
        data.forEach { slice ->
            val currentSliceAngle = calculateAngles(slice.value, progress, data)
            tempData.add(
                PieChartSliceData(
                    drawablePieRect,
                    startAngle,
                    currentSliceAngle,
                    true,
                    Paint().apply {
                        color = slice.color
                    }
                )
            )
            startAngle += currentSliceAngle
        }
        return PieChartSlicesData(tempData)
    }
}
