package com.example.composegraphlibrary.linegraph.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.linegraph.data.GraphConstants.NUMBER_OF_Y_LABELS
import com.example.composegraphlibrary.linegraph.data.LineGraphUtils
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantDottedLineColor
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantLineWidth
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantPathLineColor
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantPointColor
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantPointWidth
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantYLineColor

class QuadrantDrawer(
    private val canvas: Canvas,
    private val quadrantRect: Rect,
    private val data: LineGraphUtils,
) {
    fun drawDataPoints(progress: Float) {
        val paint = Paint().apply {
            color = quadrantPathLineColor
            style = PaintingStyle.Stroke
            strokeWidth = quadrantLineWidth
        }
        var previousPointLocation: Offset? = null

        data.getDataPoints(quadrantRect).forEachIndexed { index, pair ->
            if (index > 0) {
                canvas.drawLine(
                    p1 = previousPointLocation!!,
                    p2 = Offset(
                        x = (pair.first.x - previousPointLocation!!.x) * progress + previousPointLocation!!.x,
                        y = (pair.first.y - previousPointLocation!!.y) * progress + previousPointLocation!!.y
                    ),
                    paint = paint
                )
            }
            previousPointLocation = pair.first

            drawPoint(
                canvas = canvas,
                center = pair.first,
                progress = progress
            )
        }
    }

    private fun drawPoint(
        canvas: Canvas,
        center: Offset,
        progress: Float
    ) {
        val paint = Paint().apply {
            color = quadrantPointColor
            strokeWidth = quadrantPointWidth
            alpha = progress
        }

        canvas.drawCircle(center, 9.dp.value, paint)
    }

    fun drawQuadrantLines() {
        val linePaint = Paint()
        linePaint.apply {
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            strokeWidth = quadrantLineWidth
            color = quadrantDottedLineColor
        }

        data.yLabelValues.forEachIndexed { index, _ ->
            var y = quadrantRect.bottom * ((index) / NUMBER_OF_Y_LABELS)
            if (index == 0) {
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

    fun drawYLine() {
        val x = quadrantRect.right
        val axisLinePaint = Paint().apply {
            color = quadrantYLineColor
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
