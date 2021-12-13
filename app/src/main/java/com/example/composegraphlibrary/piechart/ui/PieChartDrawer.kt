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

    fun drawLabels() {
        val labelPaint = Paint()
        val valuePaint = Paint()
        data.listOfPieData.forEachIndexed { index, slice ->
            val sizeDivision = 1f / data.listOfPieData.size
            val textRect = setTextSizeForWidth(
                labelPaint,
                (drawablePieLabelRect.width * sizeDivision) / 2f,
                slice.label
            )

            val xPosition = (drawablePieLabelRect.width * (sizeDivision * index))

            // label
            canvas.nativeCanvas.drawText(
                slice.label,
                xPosition,
                drawablePieLabelRect.top + textRect.height,
                labelPaint.asFrameworkPaint()
            )

            // value
            setTextSizeForHeight(valuePaint, (drawablePieLabelRect.height - textRect.height), slice.value.toString())
            canvas.nativeCanvas.drawText(
                slice.value.toString(),
                xPosition,
                drawablePieLabelRect.top + textRect.height * 3f,
                labelPaint.asFrameworkPaint()
            )

            val radius = textRect.height * 0.7f
            canvas.drawCircle(
                center = Offset(
                    xPosition + textRect.right + textRect.height ,
                    drawablePieLabelRect.top + textRect.height * 0.5f
                ),
                radius = radius,
                paint = Paint().apply {
                    color = slice.color
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
        val testTextSize = 48f
        val bounds = android.graphics.Rect()
        with(paint.asFrameworkPaint()) {
            textSize = testTextSize
            getTextBounds(text, 0, text.length, bounds)
            textSize = testTextSize * desiredWidth / bounds.width()
            getTextBounds(text, 0, text.length, bounds)
        }

        return bounds.toComposeRect()
    }

    private fun setTextSizeForHeight(
        paint: Paint,
        desiredHeight: Float,
        text: String
    ): Rect {
        val testTextSize = 48f
        val bounds = android.graphics.Rect()
        with(paint.asFrameworkPaint()) {
            textSize = testTextSize
            getTextBounds(text, 0, text.length, bounds)
            textSize = testTextSize * desiredHeight / bounds.height()
            getTextBounds(text, 0, text.length, bounds)
        }

        return bounds.toComposeRect()
    }
    fun drawRect(size: Size) {
        canvas.drawRect(size.toRect(), Paint().apply {
            color = Color.Blue
            style = Stroke
        })
    }
}
