package com.example.composegraphlibrary.linegraph.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.TextUnit
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.linegraph.data.LineGraphUtils
import com.example.composegraphlibrary.linegraph.data.StyleConfig.xAxisLabelSize
import com.example.composegraphlibrary.linegraph.data.StyleConfig.xAxisLineColour
import com.example.composegraphlibrary.linegraph.data.StyleConfig.xAxisLineWidth

class XAxisDrawer(
    private val xAxisRect: Rect,
    private val canvas: Canvas,
    private val data: LineGraphUtils,
    private val lineWidth: Float = xAxisLineWidth,
    private val color: Color = xAxisLineColour
) {


    fun drawXAxisLine() {
        val yPoint = xAxisRect.top + (lineWidth / 2f)
        val axisLinePaint = Paint().apply {
            color = color
            strokeWidth = lineWidth
        }

        canvas.drawLine(
            p1 = Offset(
                x = xAxisRect.left - 5f,
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
            Utils.setTextSizeForWidth(paint, (xAxisRect.width * (1f / (data.listOfData.size - 1f))), it.xLabel, true)
            if (paint.asFrameworkPaint().textSize < textSize) {
                textSize = paint.asFrameworkPaint().textSize
            }
        }
        paint.asFrameworkPaint().textSize = textSize

        data.getDataPoints(xAxisRect).forEach {
            canvas.nativeCanvas.drawText(
                it.second.xLabel,
                xAxisRect.left + it.first.x,
                xAxisRect.top + paint.asFrameworkPaint().textSize * 1.5f,
                paint.asFrameworkPaint()
            )
        }
    }
}
