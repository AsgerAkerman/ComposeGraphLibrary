package com.example.composegraphlibrary.piechart.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toComposeRect
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.linegraph.data.StyleConfig
import com.example.composegraphlibrary.piechart.data.PieChartValues

class PieChartDrawer(
    private val data: PieChartValues,
    private val canvas: Canvas,
    private val drawablePieRect: Rect,
    private val drawablePieLabelRect: Rect,
    private val progress: Float,
) {

    fun drawPieChart() {
        var startAngle = 0f
        data.listOfPieData.forEach { slice ->
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
