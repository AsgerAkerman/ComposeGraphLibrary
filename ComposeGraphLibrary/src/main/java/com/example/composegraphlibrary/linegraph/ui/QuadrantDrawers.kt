package com.example.composegraphlibrary.linegraph.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.linegraph.data.CirclePointsData
import com.example.composegraphlibrary.linegraph.data.LineChartStyleConfig
import com.example.composegraphlibrary.linegraph.data.LineData
import com.example.composegraphlibrary.linegraph.data.QuadrantDataPoints

fun DrawScope.drawQuadrantLines(quadrantDataPoints: QuadrantDataPoints) {
    quadrantDataPoints.linePoints.forEach {
        drawContext.canvas.drawLine(
            p1 = Offset(
                x = it.linePoints.first.x,
                y = it.linePoints.first.y
            ),
            p2 = Offset(
                x = it.linePoints.second.x,
                y = it.linePoints.second.y
            ),
            paint = it.paint
        )
    }
}

fun DrawScope.drawQuadrantYLine(quadrantYLineData: LineData) {
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

fun DrawScope.drawDataPoints(quadrantDataPoints: QuadrantDataPoints) {
    quadrantDataPoints.linePoints.forEach {
        drawContext.canvas.drawLine(
            p1 = it.linePoints.first,
            p2 = it.linePoints.second,
            paint = it.paint
        )
    }
}

fun DrawScope.drawCircles(data: CirclePointsData) {
    data.list.forEach {
        drawContext.canvas.drawCircle(it.point, 9.dp.value, it.paint)
    }
}

