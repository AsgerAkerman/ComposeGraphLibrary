package com.example.composegraphlibrary.barchart.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.example.composegraphlibrary.barchart.data.Labels
import com.example.composegraphlibrary.barchart.data.LineData
import com.example.composegraphlibrary.linegraph.data.Label

fun DrawScope.drawYAxisLine(yAxisLineData: LineData) {
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

fun DrawScope.drawYLabels(yLabels: Labels) {
    yLabels.labels.forEach {
        drawContext.canvas.nativeCanvas.drawText(
            it.label,
            it.point.x,
            it.point.y,
            it.paint.asFrameworkPaint()
        )
    }
}

fun DrawScope.drawYUnit(label: Label) {
    drawContext.canvas.nativeCanvas.drawText(
        label.label,
        label.point.x,
        label.point.y,
        label.paint.asFrameworkPaint()
    )
}
