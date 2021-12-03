package com.example.composegraphlibrary.piechart.data

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size

object PieChartRectCalculator {
    fun computeLabelRect(
        size: Size,
    ): Rect {
        val bottom = size.width * 1.2f
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
        val left = size.width * 0.1f
        val top = size.width * 0.1f
        return Rect(
            left = left,
            top = top,
            bottom = pieLabelRect.top * 0.9f,
            right = size.width * 0.9f
        )
    }
}
