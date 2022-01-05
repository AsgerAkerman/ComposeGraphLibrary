package com.example.composegraphlibrary.barchart.data

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import com.example.composegraphlibrary.linegraph.data.GraphConstants

object BarChartRectCalculator {
    fun computeBarYAxisRect(
        size: Size,
    ): Rect {
        val right = size.width * GraphConstants.SIDE_PADDING_VALUE
        val height = size.height * GraphConstants.SIDE_PADDING_VALUE
        return Rect(
            left = 0f,
            top = 0f,
            bottom = size.height - height,
            right = right
        )
    }

    fun computeBarXAxisRect(
        yAxisRectWidth: Float,
        size: Size,
    ): Rect {
        val height = size.height * GraphConstants.SIDE_PADDING_VALUE
        val top = size.height - height
        val right = size.width * GraphConstants.SIDE_PADDING_VALUE
        return Rect(
            left = yAxisRectWidth,
            top = top,
            bottom = size.height,
            right = size.width - right
        )
    }

    fun computeBarQuadrantRect(
        xAxisRect: Rect,
        yAxisRect: Rect,
        size: Size,
    ): Rect {
        val right = size.width * GraphConstants.SIDE_PADDING_VALUE

        return Rect(
            left = yAxisRect.width,
            top = 0f,
            bottom = xAxisRect.top,
            right = size.width - right
        )
    }
}
