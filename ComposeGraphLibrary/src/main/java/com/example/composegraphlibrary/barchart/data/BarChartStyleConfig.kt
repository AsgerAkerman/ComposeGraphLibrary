package com.example.composegraphlibrary.barchart.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class BarChartStyleConfig(
    var quadrantDottedLineColor: Color = Color.LightGray,
    var quadrantLineWidth: Float = 3f,

    var xAxisLineColor: Color = Color.Gray,
    var xAxisLineWidth: Float = 4f,

    var yAxisLineColor: Color = Color.Gray,
    var yAxisLabelSize: TextUnit = 44.sp,
    var yAxisLineWidth: Float = 4f
) {
}
