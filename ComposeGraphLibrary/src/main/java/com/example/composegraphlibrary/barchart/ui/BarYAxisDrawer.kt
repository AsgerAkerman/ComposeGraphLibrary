package com.example.composegraphlibrary.barchart.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.TextUnit
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.barchart.data.BarChartValues
import com.example.composegraphlibrary.linegraph.data.GraphConstants
import com.example.composegraphlibrary.linegraph.data.StyleConfig

class BarYAxisDrawer(
    private val canvas: Canvas,
    private val yAxisRect: Rect,
    private val data: BarChartValues,
    private val labelSize: TextUnit = StyleConfig.yAxisLabelSize,
    private val lineWidth: Float = StyleConfig.yAxisLineWidth,
    private val colour: Color = StyleConfig.yAxisLineColour
) {
    fun drawYAxisLine() {
        val x = yAxisRect.right - lineWidth
        val axisLinePaint = Paint().apply {
            color = colour
            strokeWidth = lineWidth
        }

        canvas.drawLine(
            p1 = Offset(
                x = x,
                y = yAxisRect.top
            ),
            p2 = Offset(
                x = x,
                y = yAxisRect.bottom
            ),
            paint = axisLinePaint
        )
    }

    fun drawLabels() {
        val labelPaint = Paint()
        Utils.setTextSizeForWidth(labelPaint, yAxisRect.width, data.yLabelValues.maxOf { it.toFloat() }.toString(), false)

        data.yLabelValues.forEachIndexed { index, label ->
            val labelValue = labelSize.value / 2
            val x = yAxisRect.left
            var y = yAxisRect.bottom * ((index) / GraphConstants.NUMBER_OF_Y_LABELS) + labelValue
            if (index == 0) {
                y = (yAxisRect.bottom * 0f) + labelValue
            }
            canvas.nativeCanvas.drawText(label, x, y, labelPaint.asFrameworkPaint())
        }
    }
}