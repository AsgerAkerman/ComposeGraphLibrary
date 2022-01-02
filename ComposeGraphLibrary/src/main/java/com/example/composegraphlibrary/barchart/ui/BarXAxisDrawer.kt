package com.example.composegraphlibrary.barchart.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.barchart.data.BarChartUtils

class BarXAxisDrawer(
    private val canvas: Canvas,
    private val xAxisRect: Rect,
    private val data: BarChartUtils,
    private val xAxislineWidth: Float,
    private val xAxisLineColor: Color
) {

    fun drawXAxisLine() {
        val yPoint = xAxisRect.top + (xAxislineWidth / 2f)
        val axisLinePaint = Paint().apply {
            color = xAxisLineColor
            strokeWidth = xAxislineWidth
        }

        canvas.drawLine(
            p1 = Offset(
                x = xAxisRect.left,
                y = yPoint
            ),
            p2 = Offset(
                x = xAxisRect.right,
                y = yPoint
            ),
            paint = axisLinePaint
        )
    }

    fun drawLabels() {
        val paint = Paint()
        var textSize = 50f

        data.listOfData.forEach {
            Utils.setTextSizeForWidth(paint, (xAxisRect.width * (1f / data.listOfData.size)) - 10f, it.xLabel, true)
            if (paint.asFrameworkPaint().textSize < textSize) {
                textSize = paint.asFrameworkPaint().textSize
            }
        }
        paint.asFrameworkPaint().textSize = textSize

        data.listOfData.forEachIndexed { index, dataObject ->
            val distanceBetweenLabels = (xAxisRect.width / data.listOfData.size) * index
            canvas.nativeCanvas.drawText(
                dataObject.xLabel,
                (xAxisRect.left + distanceBetweenLabels),
                xAxisRect.top + paint.asFrameworkPaint().textSize * 1.5f,
                paint.asFrameworkPaint()
            )
        }
    }
}
