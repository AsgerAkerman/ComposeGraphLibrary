package com.example.composegraphlibrary.piechart.data

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size

object PieChartRectCalculator {
    fun computePieRect(
        size: Size,
    ): Rect {
        return Rect(
            left = 0f,
            top = 0f,
            bottom = size.width,
            right = size.width
        )
    }
}
