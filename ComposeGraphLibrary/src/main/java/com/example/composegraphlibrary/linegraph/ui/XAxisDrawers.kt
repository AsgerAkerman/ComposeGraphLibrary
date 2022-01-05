package com.example.composegraphlibrary.linegraph.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.example.composegraphlibrary.linegraph.data.XAxisLineData
import com.example.composegraphlibrary.linegraph.data.XLabels

fun DrawScope.drawXAxisLine(lineData: XAxisLineData) {
    drawContext.canvas.drawLine(
        p1 = Offset(
            x = lineData.linePoints.first.x,
            y = lineData.linePoints.first.y
        ),
        p2 = Offset(
            x = lineData.linePoints.second.x,
            y = lineData.linePoints.second.y
        ),
        paint = lineData.paint
    )
}

fun DrawScope.drawXLabels(xLabels: XLabels) {
    xLabels.labels.forEach {
        drawContext.canvas.nativeCanvas.drawText(
            it.label,
            it.point.x,
            it.point.y,
            it.paint.asFrameworkPaint()
        )
    }
}
