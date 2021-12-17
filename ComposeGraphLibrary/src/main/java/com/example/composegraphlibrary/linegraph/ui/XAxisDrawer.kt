package com.example.composegraphlibrary.linegraph.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.TextUnit
import com.example.composegraphlibrary.Utils
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
            Utils.setTextSizeForWidth(paint,(xAxisRect.width * (1f / (data.listOfData.size - 1f))), it.xLabel, true)
            if(paint.asFrameworkPaint().textSize < textSize) {
                textSize = paint.asFrameworkPaint().textSize
            }
        }
        paint.asFrameworkPaint().textSize = textSize

        data.listOfData.forEachIndexed { index, dataObject ->
            val distanceBetweenLabels = (xAxisRect.width / (data.listOfData.size - 1f)) * (index)
            canvas.nativeCanvas.drawText(
                dataObject.xLabel,
                ((xAxisRect.left - 20f) + distanceBetweenLabels),
                xAxisRect.top + paint.asFrameworkPaint().textSize * 1.5f,
                paint.asFrameworkPaint()
            )
        }
    }
}