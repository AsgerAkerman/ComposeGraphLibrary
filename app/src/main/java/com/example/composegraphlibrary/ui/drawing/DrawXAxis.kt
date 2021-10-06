package com.example.composegraphlibrary.ui.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

class DrawXAxis(
    private val lineWidth: Float,
    private val lineColour: Color,
    private val fontSize: Float
) {
    fun drawXAxisLine(
        drawScope: DrawScope,
    ) {
        with(drawScope) {
            drawLine(
                start = Offset(x = fontSize, y = size.height - fontSize),
                end = Offset(x = size.width - fontSize, y = size.height - fontSize),
                color = lineColour,
                strokeWidth = lineWidth,
            )
        }
    }
}
