package com.example.composegraphlibrary.barchart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.TextUnit
import com.example.composegraphlibrary.linegraph.data.StyleConfig

class BarXAxisDrawer(
    private val canvas: Canvas,
    private val xAxisRect: Rect,
    private val data: BarChartValues,
    private val labelSize: TextUnit = StyleConfig.xAxisLabelSize,
    private val lineWidth: Float = StyleConfig.xAxisLineWidth,
    private val colour: Color = StyleConfig.xAxisLineColour
) {

    fun drawXAxisLine() {
        val yPoint = xAxisRect.top + (lineWidth / 2f)
        val axisLinePaint = Paint().apply {
            color = colour
            strokeWidth = lineWidth
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
        val paint = Paint().asFrameworkPaint()
        paint.apply {
            textSize = labelSize.value
        }

        data.listOfData.forEachIndexed { index, dataObject ->
            val distanceBetweenLabels = xAxisRect.width * ((1f / data.listOfData.size) * (index))
            if (distanceBetweenLabels > xAxisRect.width) {
                return
            }

            canvas.nativeCanvas.drawText(
                dataObject.xLabel,
                xAxisRect.left + distanceBetweenLabels,
                xAxisRect.top + paint.textSize,
                paint
            )
        }
    }
}
