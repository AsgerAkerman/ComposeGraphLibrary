package com.example.composegraphlibrary.linegraph.data

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import com.example.composegraphlibrary.linegraph.data.GraphConstants.SIDE_PADDING_VALUE

object LineGraphRectCalculator {
    fun computeYAxisRect(
        size: Size,
    ): Rect {
        val right = size.width * SIDE_PADDING_VALUE
        val height = size.height * SIDE_PADDING_VALUE
        return Rect(
            left = 0f,
            top = 0f,
            bottom = size.height - height,
            right = right
        )
    }

    fun computeXAxisRect(
        yAxisRectWidth: Rect,
        size: Size,
    ): Rect {
        val top = size.height
        val right = size.width * SIDE_PADDING_VALUE
        val height = size.height * SIDE_PADDING_VALUE
        return Rect(
            left = yAxisRectWidth.right,
            top = top,
            bottom = size.height - height,
            right = size.width - right
        )
    }

    fun computeQuadrantRect(
        xAxisRect: Rect,
        yAxisRect: Rect,
        size: Size,
    ): Rect {
        val right = size.width * SIDE_PADDING_VALUE

        return Rect(
            left = yAxisRect.width,
            top = 0f,
            bottom = xAxisRect.top,
            right = size.width - right
        )
    }
}
