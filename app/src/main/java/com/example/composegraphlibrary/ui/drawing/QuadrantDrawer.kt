package com.example.composegraphlibrary.ui.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.data.GraphConstants.NUMBER_OF_Y_LABELS
import com.example.composegraphlibrary.data.LineGraphValues
import com.example.composegraphlibrary.data.StyleConfig.quadrantDottedLineColor
import com.example.composegraphlibrary.data.StyleConfig.quadrantLineWidth
import com.example.composegraphlibrary.data.StyleConfig.quadrantPathLineColor
import com.example.composegraphlibrary.data.StyleConfig.quadrantPointColor
import com.example.composegraphlibrary.data.StyleConfig.quadrantPointWidth
import com.example.composegraphlibrary.data.StyleConfig.quadrantYLineColor

class QuadrantDrawer(
    private val canvas: Canvas,
    private val quadrantRect: Rect,
    private val data: LineGraphValues,
) {
    fun drawDataPoints(alpha: Float) {
        val path = Path()
        data.getDataPoints(quadrantRect).forEachIndexed { index, pair ->
            if (index == 0) {
                path.moveTo(pair.first.x, pair.first.y)
            }

            path.lineTo(pair.first.x, pair.first.y)
            drawPoint(
                canvas = canvas,
                center = pair.first,
                alphaa = alpha,
            )
        }

        drawPath(canvas = canvas, path, alpha)
    }

    private fun drawPoint(
        canvas: Canvas,
        center: Offset,
        alphaa: Float
    ) {
        val paint = Paint().apply {
            color = quadrantPointColor
            style = PaintingStyle.Stroke
            alpha = alphaa
            strokeWidth = quadrantPointWidth
        }

        canvas.drawCircle(center, 9.dp.value, paint)
    }

    fun drawPath(
        canvas: Canvas,
        path: Path,
        alphaa: Float
    ) {
        val paint = Paint().apply {
            color = quadrantPathLineColor
            style = PaintingStyle.Stroke
            strokeWidth = quadrantLineWidth
            alpha = alphaa
        }

        canvas.drawPath(path, paint)
    }

    fun drawQuadrantLines() {
        val linePaint = Paint()
        linePaint.apply {
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            strokeWidth = quadrantLineWidth
            color = quadrantDottedLineColor
        }

        data.yLabelValues.forEachIndexed { index, label ->
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
