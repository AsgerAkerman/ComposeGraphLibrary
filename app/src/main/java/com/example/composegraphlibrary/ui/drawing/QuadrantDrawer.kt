package com.example.composegraphlibrary.ui.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.data.LineGraphValues
import com.example.composegraphlibrary.data.StyleConfig.quadrantLineColor
import com.example.composegraphlibrary.data.StyleConfig.quadrantLineWidth
import com.example.composegraphlibrary.data.StyleConfig.quadrantPointColor

class QuadrantDrawer(
    private val canvas: Canvas,
    private val quadrantRect: Rect,
    private val data: LineGraphValues,
) {
    fun drawDataPoints() {
        val path = Path()
        data.getDataPoints(quadrantRect).forEachIndexed { index, pair ->
            if (index == 0) {
                path.moveTo(pair.first.x, pair.first.y)
            }

            path.lineTo(pair.first.x, pair.first.y)
            drawPoint(
                canvas = canvas,
                center = pair.first
            )
        }

        drawPath(canvas = canvas, path)
    }


    private fun drawPoint(
        canvas: Canvas,
        center: Offset,
    ) {
        val paint = Paint().apply {
            color = quadrantPointColor
            style = PaintingStyle.Fill
        }

        canvas.drawCircle(center, 8.dp.value / 2f, paint)
    }

    fun drawPath(
        canvas: Canvas,
        path: Path
    ) {
        val paint = Paint().apply {
            color = Color.Blue
            style = PaintingStyle.Stroke
            strokeWidth = quadrantLineWidth
        }

        canvas.drawPath(path, paint)
    }

    fun drawQuadrantLines() {
        val linePaint = Paint()
        linePaint.apply {
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        }

        data.yLabelValues.forEachIndexed { index, label ->
            var y = quadrantRect.bottom * ((index) / 4f)
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
            color = quadrantLineColor
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
