package com.example.composegraphlibrary.barchart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import com.example.composegraphlibrary.linegraph.data.GraphConstants
import com.example.composegraphlibrary.linegraph.data.StyleConfig

class BarQuadrantDrawer(
    private val canvas: Canvas,
    private val quadrantRect: Rect,
    private val data: BarChartValues,
) {
    fun drawQuadrantLines() {
        val linePaint = Paint()
        linePaint.apply {
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            strokeWidth = StyleConfig.quadrantLineWidth
            color = StyleConfig.quadrantDottedLineColor
        }

        repeat(GraphConstants.NUMBER_OF_Y_LABELS.toInt()) {
            var y = quadrantRect.bottom * ((it) / GraphConstants.NUMBER_OF_Y_LABELS)
            if (it == 0) {
                y = (quadrantRect.bottom * 0f)
            }

            canvas.drawLine(
                p1 = Offset(
                    x = quadrantRect.left,
                    y = y
                ),
                p2 = Offset(
                    x = quadrantRect.right,
                    y = y
                ),
                paint = linePaint
            )
        }
    }

    fun drawBarCharts(progress: Float) {
        val axisLinePaint = Paint().apply {
            color = StyleConfig.quadrantPointColor
            strokeWidth = StyleConfig.quadrantLineWidth
        }

        data.listOfData.forEachIndexed { _, dataObject ->
            val axisSeparator = (quadrantRect.width * ((1f / data.listOfData.size)))

            dataObject.categories.forEachIndexed { index, categories ->
                canvas.drawRect(
                    left = (quadrantRect.left) + (axisSeparator * index),
                    bottom = quadrantRect.bottom,
                    right = axisSeparator * (index + 1f),
                    top = data.getYPoint(quadrantRect, dataObject.categories[index].value) * progress,
                    paint = axisLinePaint
                )
            }
        }
    }

    fun drawYLine() {
        val x = quadrantRect.right
        val axisLinePaint = Paint().apply {
            color = Color.LightGray
            strokeWidth = StyleConfig.quadrantLineWidth
        }

        canvas.drawLine(
            p1 = Offset(
                x = x,
                y = quadrantRect.top
            ),
            p2 = Offset(
                x = x,
                y = quadrantRect.bottom
            ),
            paint = axisLinePaint
        )
    }
}
