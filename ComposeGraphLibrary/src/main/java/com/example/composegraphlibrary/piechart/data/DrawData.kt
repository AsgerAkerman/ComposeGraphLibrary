package com.example.composegraphlibrary.piechart.data

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Paint

data class PieChartSliceData(val rect: Rect, val startAngle: Float, val sweepAngle: Float, val useCenter: Boolean, val paint: Paint)

data class PieChartSlicesData(val pieCharts: List<PieChartSliceData>)