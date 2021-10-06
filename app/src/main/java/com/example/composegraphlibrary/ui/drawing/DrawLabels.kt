package com.example.composegraphlibrary.ui.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.example.composetestproject.ui.theme.LineGraphValues

class DrawLabels(
    fontSize: Float,
    drawScope: DrawScope,
    pointData: MutableList<LineGraphValues.Point>
) {


    fun drawLabels(drawScope: DrawScope) {
        drawScope.drawIntoCanvas { canvas ->
            // Should just be a draw y labels function
            canvas.nativeCanvas.drawText(
                lineGraphValue.maxYValue.toInt().toString(),
                0f,
                0f,
                paint
            )

            data.forEachIndexed { index, dataPoint ->
                val distanceBetweenLabels = fontSize + index * fontSize * 2
                if (distanceBetweenLabels > canvasWidth) {
                    return@forEachIndexed
                }
                canvas.nativeCanvas.drawText(
                    dataPoint.first.toString(),
                    distanceBetweenLabels,
                    canvasHeight,
                    paint
                )

                val yPoint = 1 - (dataPoint.third.toFloat() / lineGraphValue.maxYValue)

                path.lineTo(distanceBetweenLabels, (canvasHeight - 200f) * yPoint)

                drawPoint(
                    drawScope = drawScope,
                    canvas,
                    Offset(distanceBetweenLabels, (canvasHeight - 200f) * yPoint)
                )
            }
            drawPath(canvas = canvas, path)
        }
    }

    fun drawPoint(
        canvas: Canvas,
        center: Offset,
        drawScope: DrawScope
    ) {
        val paint = Paint().apply {
            color = Color.Blue
            style = PaintingStyle.Fill
            isAntiAlias = true
        }
        with(drawScope as Density) {
            canvas.drawCircle(center, 8.dp.toPx() / 2f, paint)
        }
    }
}


