package com.example.composegraphlibrary.linegraph.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.linegraph.data.LineChartStyleConfig
import com.example.composegraphlibrary.linegraph.data.QuadrantDataPoints
import com.example.composegraphlibrary.linegraph.data.QuadrantYLineData

fun DrawScope.drawQuadrantLines(quadrantDataPoints: QuadrantDataPoints) {
    quadrantDataPoints.linePoints.forEach {
        drawContext.canvas.drawLine(
            p1 = Offset(
                x = it.linePoint.first.x,
                y = it.linePoint.first.y
            ),
            p2 = Offset(
                x = it.linePoint.second.x,
                y = it.linePoint.second.y
            ),
            paint = it.paint
        )
    }
}

fun DrawScope.drawQuadrantYLine(quadrantYLineData: QuadrantYLineData) {
    drawContext.canvas.drawLine(
        p1 = Offset(
            x = quadrantYLineData.linePoints.first.x,
            y = quadrantYLineData.linePoints.first.y
        ),
        p2 = Offset(
            x = quadrantYLineData.linePoints.second.x,
            y = quadrantYLineData.linePoints.second.y
        ),
        paint = quadrantYLineData.paint
    )
}

fun DrawScope.drawDataPoints(
    quadrantDataPoints: QuadrantDataPoints,
    progress: Float,
    styleConfig: LineChartStyleConfig
) {
    quadrantDataPoints.linePoints.forEach {
        drawContext.canvas.drawLine(
            p1 = it.linePoint.first,
            p2 = it.linePoint.second,
            paint = it.paint
        )

        drawPoint(
            canvas = drawContext.canvas,
            center = it.linePoint.first,
            progress = progress,
            styleConfig = styleConfig
        )
    }
}

private fun drawPoint(
    canvas: Canvas,
    center: Offset,
    progress: Float,
    styleConfig: LineChartStyleConfig
) {
    val paint = Paint().apply {
        color = styleConfig.quadrantPointColor
        strokeWidth = styleConfig.quadrantPointWidth
        alpha = progress
    }

    canvas.drawCircle(center, 9.dp.value, paint)
}
