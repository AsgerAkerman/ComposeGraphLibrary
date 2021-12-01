package com.example.composegraphlibrary.piechart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.linegraph.data.StyleConfig

class PieChartDrawer(
    private val data: PieChartValues,
    private val canvas: Canvas,
    private val drawablePieRect: Rect,
    private val drawablePieLabelRect: Rect,
) {

    fun drawPieChart() {
        var startArc = 0f
        data.pieSlices.forEach { slice ->
            canvas.drawArc(
                rect = drawablePieRect,
                startAngle = startArc,
                sweepAngle = slice.second,
                useCenter = true,
                paint = Paint().apply {
                    color = slice.first.color
                }
            )

            startArc += slice.second
        }
    }

    fun drawLabels() {
        val labelPaint = Paint()
        data.pieSlices.forEachIndexed { index, slice ->
            val textRect = setTextSizeForWidth(
                labelPaint,
                (drawablePieLabelRect.width * 0.2f) / 2f,
                slice.first.label
            )

            canvas.nativeCanvas.drawText(
                slice.first.label,
                (drawablePieLabelRect.width * (0.2f * index)),
                drawablePieLabelRect.bottom - (drawablePieLabelRect.height / 2f),
                labelPaint.asFrameworkPaint()
            )

            canvas.drawCircle(
                center = Offset(
                    (drawablePieLabelRect.width * (0.2f * index)) + textRect.right + 20f * 2f,
                    drawablePieLabelRect.bottom - (drawablePieLabelRect.height / 2f)
                ),
                radius = 20f,
                paint = Paint().apply {
                    color = slice.first.color
                    strokeWidth = StyleConfig.quadrantPointWidth
                }
            )
        }
    }


    private fun setTextSizeForWidth(
        paint: Paint,
        desiredWidth: Float,
        text: String
    ): Rect {
        val testTextSize = 100f
        paint.asFrameworkPaint().textSize = testTextSize
        val bounds = android.graphics.Rect()
        paint.asFrameworkPaint().getTextBounds(text, 0, text.length, bounds)
        paint.asFrameworkPaint().textSize = testTextSize * desiredWidth / bounds.width()
        paint.asFrameworkPaint().getTextBounds(text, 0, text.length, bounds)
        return bounds.toComposeRect()
    }


    fun drawRect() {
        canvas.drawRect(drawablePieLabelRect, Paint().apply {
            color = Color.Blue
            style = Stroke
        })

        canvas.drawRect(drawablePieRect, Paint().apply {
            style = Stroke
            color = Color.Blue

        })
    }

}
