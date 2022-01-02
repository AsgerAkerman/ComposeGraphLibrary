package com.example.composegraphlibrary.linegraph.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class LineChartStyleConfig(
    var quadrantYLineColor: Color = Color.Gray,
    var quadrantDottedLineColor: Color = Color.LightGray,
    var quadrantPathLineColor: Color = Color.Blue,
    var quadrantPointColor: Color = Color.Blue,
    var quadrantPointWidth: Float = 4f,
    var quadrantLineWidth: Float = 3f,

    var xAxisLineColor: Color = Color.Gray,
    var xAxisLineWidth: Float = 4f,

    var yAxisLineColor: Color = Color.Gray,
    var yAxisLabelSize: TextUnit = 44.sp,
    var yAxisLineWidth: Float = 4f
) {
}
