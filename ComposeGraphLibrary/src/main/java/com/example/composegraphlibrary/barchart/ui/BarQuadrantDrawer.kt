package com.example.composegraphlibrary.barchart.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import com.example.composegraphlibrary.barchart.data.BarChartUtils
import com.example.composegraphlibrary.linegraph.data.GraphConstants

class BarQuadrantDrawer(
    private val canvas: Canvas,
    private val quadrantRect: Rect,
    private val data: BarChartUtils,
    private val quadrantLineWidth: Float,
    private val quadrantDottedLineColor: Color
    ) {
    fun drawQuadrantLines() {
        val linePaint = Paint()
        linePaint.apply {
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            strokeWidth = quadrantLineWidth
            color = quadrantDottedLineColor
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
        data.listOfData.forEachIndexed { brandIndex, brandObject ->
            val categories = brandObject.categories

            val axisSeparator = (quadrantRect.width * ((1f / data.listOfData.size)))

            val xStart = (quadrantRect.left) + (axisSeparator * brandIndex)
            val xStartForCalc = (axisSeparator * brandIndex)

            val xEnd = axisSeparator * (brandIndex + 1f)
            val xDelta = ((xEnd - xStartForCalc) / categories.size) * 0.90f

            categories.forEachIndexed { index, category ->
                canvas.drawRect(
                    left = xStart + (xDelta * index),
                    bottom = quadrantRect.bottom,
                    right = xStart + (xDelta * (index + 1f)),
                    top = data.getYPoint(quadrantRect, category.value) * progress,
                    paint = Paint().apply {
                        color = category.color
                    }
                )
            }
        }
    }

    fun drawYLine() {
        val x = quadrantRect.right
        val axisLinePaint = Paint().apply {
            color = Color.LightGray
            strokeWidth = quadrantLineWidth
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
