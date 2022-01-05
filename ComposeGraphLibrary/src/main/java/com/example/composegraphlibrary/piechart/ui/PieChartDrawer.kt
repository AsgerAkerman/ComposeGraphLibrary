package com.example.composegraphlibrary.piechart.ui

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.composegraphlibrary.piechart.data.PieChartSlicesData

fun DrawScope.drawPieChart(data: PieChartSlicesData) {
    data.pieCharts.forEach { slice ->
        drawContext.canvas.drawArc(
            rect = slice.rect,
            startAngle = slice.startAngle,
            sweepAngle = slice.sweepAngle,
            useCenter = slice.useCenter,
            paint = slice.paint
        )
    }
}
