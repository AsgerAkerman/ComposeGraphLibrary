package com.example.composegraphlibrary.barchart.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.example.composegraphlibrary.barchart.data.YAxisLineData
import com.example.composegraphlibrary.barchart.data.YLabels

fun DrawScope.drawYAxisLine(yAxisLineData: YAxisLineData) {
    drawContext.canvas.drawLine(
        p1 = Offset(
            x = yAxisLineData.linePoints.first.x,
            y = yAxisLineData.linePoints.first.y
        ),
        p2 = Offset(
            x = yAxisLineData.linePoints.second.x,
            y = yAxisLineData.linePoints.second.y
        ),
        paint = yAxisLineData.paint
    )
}

fun DrawScope.drawYLabels(yLabels: YLabels) {
    yLabels.labels.forEach {
        drawContext.canvas.nativeCanvas.drawText(
            it.label,
            it.point.x,
            it.point.y,
            it.paint.asFrameworkPaint()
        )
    }
}
