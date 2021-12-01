package com.example.composegraphlibrary.linegraph.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.TextUnit
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_LABEL_INTERVAL
import com.example.composegraphlibrary.linegraph.data.StyleConfig.xAxisLabelSize
import com.example.composegraphlibrary.linegraph.data.StyleConfig.xAxisLineColour
import com.example.composegraphlibrary.linegraph.data.StyleConfig.xAxisLineWidth
import com.example.composegraphlibrary.linegraph.data.LineGraphValues

class XAxisDrawer(
    private val xAxisRect: Rect,
    private val canvas: Canvas,
    private val data: LineGraphValues,
    private val labelSize: TextUnit = xAxisLabelSize,
    private val lineWidth: Float = xAxisLineWidth,
    private val colour: Color = xAxisLineColour
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

        val dataInterval = (data.listOfData.size / DATASET_LABEL_INTERVAL)

        data.listOfData.forEachIndexed { index, _ ->
            val distanceBetweenLabels = (xAxisRect.width / DATASET_LABEL_INTERVAL) * (index)
            if (distanceBetweenLabels > xAxisRect.width) {
                return
            }
            val labelText = (dataInterval * index).toInt().toString()

            canvas.nativeCanvas.drawText(
                labelText,
                xAxisRect.left + distanceBetweenLabels,
                xAxisRect.top + paint.textSize,
                paint
            )
        }
    }
}
