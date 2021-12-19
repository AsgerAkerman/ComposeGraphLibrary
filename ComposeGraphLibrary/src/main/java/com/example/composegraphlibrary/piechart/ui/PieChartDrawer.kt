package com.example.composegraphlibrary.piechart.ui

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import com.example.composegraphlibrary.piechart.data.PieChartUtills

class PieChartDrawer(
    private val data: PieChartUtills,
    private val canvas: Canvas,
    private val drawablePieRect: Rect,
    private val progress: Float,
) {

    fun drawPieChart() {
        var startAngle = 0f
        data.listOfSlices.forEach { slice ->
            val currentSliceAngle = data.calculateAngles(slice.value, progress)
            canvas.drawArc(
                rect = drawablePieRect,
                startAngle = startAngle,
                sweepAngle = currentSliceAngle,
                useCenter = true,
                paint = Paint().apply {
                    color = slice.color
                }
            )

            startAngle += currentSliceAngle
        }
    }
}
