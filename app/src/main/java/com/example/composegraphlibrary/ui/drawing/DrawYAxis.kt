package com.example.composegraphlibrary.ui.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

class DrawYAxis(
    private val lineWidth: Float,
    private val lineColour: Color,
    private val fontSize: Float
) {
    fun drawYAxisLine(
        drawScope: DrawScope,
    ) {
        with(drawScope) {
            drawLine(
                start = Offset(x = fontSize, y = fontSize),
                end = Offset(x = fontSize, y = this.size.height - fontSize),
                color = lineColour,
                strokeWidth = lineWidth,
            )
        }
    }
}
