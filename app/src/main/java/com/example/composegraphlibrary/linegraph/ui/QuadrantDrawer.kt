package com.example.composegraphlibrary.linegraph.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.linegraph.data.GraphConstants.NUMBER_OF_Y_LABELS
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantDottedLineColor
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantLineWidth
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantPathLineColor
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantPointColor
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantPointWidth
import com.example.composegraphlibrary.linegraph.data.StyleConfig.quadrantYLineColor
import com.example.composegraphlibrary.linegraph.data.LineGraphValues

class QuadrantDrawer(
    private val canvas: Canvas,
    private val quadrantRect: Rect,
    private val data: LineGraphValues,
) {
    fun drawDataPoints(progress: Float) {
        val paint = Paint().apply {
            color = quadrantPathLineColor
            style = PaintingStyle.Stroke
            strokeWidth = quadrantLineWidth
        }
        var prevPointLocation: Offset? = null

        data.getDataPoints(quadrantRect).forEachIndexed { index, pair ->
            if(index > 0) {
                canvas.drawLine(
                    p1 = prevPointLocation!!,
                    p2 = Offset(
                        x = (pair.first.x - prevPointLocation!!.x) * progress + prevPointLocation!!.x,
                        y = (pair.first.y - prevPointLocation!!.y) * progress + prevPointLocation!!.y
                    ),
                    paint = paint
                )
            }
            prevPointLocation = pair.first

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
            style = PaintingStyle.Stroke
            strokeWidth = quadrantPointWidth
            alpha = progress
        }

        canvas.drawCircle(center, 9.dp.value, paint)
    }

    private fun drawPath(
        canvas: Canvas,
        path: Path,
    ) {
        val paint = Paint().apply {
            color = quadrantPathLineColor
            style = PaintingStyle.Stroke
            strokeWidth = quadrantLineWidth
        }

        canvas.drawPath(path, paint)
    }

    fun drawQuadrantLines(progress: Float) {
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
                    x = quadrantRect.left * progress,
                    y = y
                ),
                p2 = Offset(
                    x = quadrantRect.right * progress,
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
