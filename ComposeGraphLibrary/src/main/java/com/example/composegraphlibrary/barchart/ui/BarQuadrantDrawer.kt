package com.example.composegraphlibrary.barchart.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.composegraphlibrary.barchart.data.LineData
import com.example.composegraphlibrary.barchart.data.LinesData
import com.example.composegraphlibrary.barchart.data.QuadrantRectsData

fun DrawScope.drawQuadrantLines(quadrantDataPoints: LinesData) {
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

fun DrawScope.drawBarCharts(data: QuadrantRectsData) {
    data.quadrantRectsData.forEach {
        drawContext.canvas.drawRect(
            left = it.rect.left,
            bottom = it.rect.bottom,
            right = it.rect.right,
            top = it.rect.top,
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
