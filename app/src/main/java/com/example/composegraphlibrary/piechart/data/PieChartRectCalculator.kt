package com.example.composegraphlibrary.piechart.data

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size

object PieChartRectCalculator {
    fun computeLabelRect(
        size: Size,
    ): Rect {
        val bottom = size.width * 1.1f
        return Rect(
            left = 0f,
            top = size.width,
            bottom = bottom,
            right = size.width
        )
    }

    fun computePieRect(
        pieLabelRect: Rect,
        size: Size,
    ): Rect {
        return Rect(
            left = 0f,
            top = 0f,
            bottom = pieLabelRect.top,
            right = size.width
        )
    }
}
