package com.example.composegraphlibrary.linegraph

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.linegraph.data.*

@Composable
fun LineChartComposable(
    data: List<LineChartDataPoint>,
    description: String,
    styleConfig: LineChartStyleConfig
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val animationTargetValue = remember { mutableStateOf(0f) }
        val animatedFloatValue = animateFloatAsState(
            targetValue = animationTargetValue.value,
            animationSpec = tween(durationMillis = 1000),
        )
        Canvas(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(10.dp)
        ) {
            animationTargetValue.value = 1f

            val height = (size.height * 0.3f)
            val yAxisRect = LineGraphRectCalculator.computeYAxisRect(height, size)
            val xAxisRect = LineGraphRectCalculator.computeXAxisRect(height, yAxisRect, size)
            val quadrantRect = LineGraphRectCalculator.computeQuadrantRect(xAxisRect, yAxisRect, size)
            val lineGraphUtils = LineGraphUtils(data)

            drawXAxisLine(xAxisRect, styleConfig)
            drawYAxisLine(yAxisRect, styleConfig)
            drawQuadrantYLine(quadrantRect, styleConfig)
            drawQuadrantLines(quadrantRect, styleConfig)
            drawXLabels(data, xAxisRect, lineGraphUtils)
            drawYLabels(yAxisRect, lineGraphUtils)
            drawDataPoints(animatedFloatValue.value, quadrantRect, lineGraphUtils, styleConfig)
        }
        Text(text = description, style = MaterialTheme.typography.body1)
    }
}


fun DrawScope.drawXAxisLine(xAxisRect: Rect, styleConfig: LineChartStyleConfig) {
    val paint = Paint().apply {
        color = styleConfig.xAxisLineColor
        strokeWidth = styleConfig.xAxisLineWidth
    }

    val coordinates = calculateXAxisLineOffset(xAxisRect, styleConfig)
    drawContext.canvas.drawLine(
        p1 = Offset(
            x = coordinates.first.x,
            y = coordinates.first.y
        ),
        p2 = Offset(
            x = coordinates.second.x,
            y = coordinates.second.y
        ),
        paint = paint
    )
}

fun DrawScope.drawYAxisLine(yAxisRect: Rect, styleConfig: LineChartStyleConfig) {
    val axisLinePaint = Paint().apply {
        color = styleConfig.yAxisLineColor
        strokeWidth = styleConfig.yAxisLineWidth
    }

    val x = yAxisRect.right - styleConfig.yAxisLineWidth
    drawContext.canvas.drawLine(
        p1 = Offset(
            x = x,
            y = yAxisRect.top
        ),
        p2 = Offset(
            x = x,
            y = yAxisRect.bottom
        ),
        paint = axisLinePaint
    )
}

fun DrawScope.drawQuadrantLines(quadrantRect: Rect, styleConfig: LineChartStyleConfig) {
    val paint = Paint().apply {
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        strokeWidth = styleConfig.quadrantLineWidth
        color = styleConfig.quadrantDottedLineColor
    }

    repeat(GraphConstants.NUMBER_OF_Y_LABELS.toInt()) {
        var y = quadrantRect.bottom * ((it) / GraphConstants.NUMBER_OF_Y_LABELS)
        if (it == 0) {
            y = (quadrantRect.bottom * 0f)
        }

        drawContext.canvas.drawLine(
            p1 = Offset(
                x = quadrantRect.left,
                y = y
            ),
            p2 = Offset(
                x = quadrantRect.right,
                y = y
            ),
            paint = paint
        )
    }
}

fun DrawScope.drawQuadrantYLine(quadrantRect: Rect, styleConfig: LineChartStyleConfig) {
    val paint = Paint().apply {
        color = styleConfig.quadrantYLineColor
        strokeWidth = styleConfig.quadrantLineWidth
    }
    val x = quadrantRect.right


    drawContext.canvas.drawLine(
        p1 = Offset(
            x = x,
            y = quadrantRect.top
        ),
        p2 = Offset(
            x = x,
            y = quadrantRect.bottom
        ),
        paint = paint
    )
}

fun DrawScope.drawXLabels(
    data: List<LineChartDataPoint>,
    xAxisRect: Rect,
    lineGraphUtils: LineGraphUtils
) {
    val paint = Paint()
    val labelTextWidth = xAxisRect.width * (1f / (data.size))
    val longestString = data.maxOf { it.xLabel }.toString()

    Utils.setTextSizeForWidth(paint, labelTextWidth, longestString, true)
    val yPaddingText = paint.asFrameworkPaint().textSize * 1.5f

    lineGraphUtils.getDataPoints(xAxisRect).forEach {
        drawContext.canvas.nativeCanvas.drawText(
            it.second.xLabel.toString(),
            it.first.x - 20f,
            xAxisRect.top + yPaddingText,
            paint.asFrameworkPaint()
        )
    }
}

fun DrawScope.drawYLabels(yAxisRect: Rect, lineGraphUtils: LineGraphUtils) {
    val paint = Paint()
    val longestString = lineGraphUtils.yLabelValues.maxOf { it.toFloat() }.toString()
    Utils.setTextSizeForWidth(paint, yAxisRect.width, longestString, false)

    lineGraphUtils.yLabelValues.forEachIndexed { index, label ->
        val x = yAxisRect.left
        var y = yAxisRect.bottom * ((index) / GraphConstants.NUMBER_OF_Y_LABELS)
        if (index == 0) {
            y = 0f
        }
        drawContext.canvas.nativeCanvas.drawText(
            label,
            x,
            y,
            paint.asFrameworkPaint()
        )
    }
}

fun DrawScope.drawDataPoints(
    progress: Float,
    quadrantRect: Rect,
    lineGraphUtils: LineGraphUtils,
    styleConfig: LineChartStyleConfig
) {
    val paint = Paint().apply {
        color = styleConfig.quadrantPathLineColor
        style = PaintingStyle.Stroke
        strokeWidth = styleConfig.quadrantLineWidth
    }
    var previousPointLocation: Offset? = null

    lineGraphUtils.getDataPoints(quadrantRect).forEachIndexed { index, pair ->
        if (index > 0) {
            drawContext.canvas.drawLine(
                p1 = previousPointLocation!!,
                p2 = Offset(
                    x = (pair.first.x - previousPointLocation!!.x) * progress + previousPointLocation!!.x,
                    y = (pair.first.y - previousPointLocation!!.y) * progress + previousPointLocation!!.y
                ),
                paint = paint
            )
        }
        previousPointLocation = pair.first

        drawPoint(
            canvas = drawContext.canvas,
            center = pair.first,
            progress = progress,
            styleConfig = styleConfig
        )
    }
}

fun calculateXAxisLineOffset(
    xAxisRect: Rect,
    styleConfig: LineChartStyleConfig
): Pair<Offset, Offset> {
    val yPoint = xAxisRect.top + (styleConfig.xAxisLineWidth / 2f)
    return Pair(
        Offset(
            x = xAxisRect.left - 5f,
            y = yPoint
        ), Offset(
            x = xAxisRect.right,
            y = yPoint
        )
    )
}

private fun drawPoint(
    canvas: Canvas,
    center: Offset,
    progress: Float,
    styleConfig: LineChartStyleConfig
) {
    val paint = Paint().apply {
        color = styleConfig.quadrantPointColor
        strokeWidth = styleConfig.quadrantPointWidth
        alpha = progress
    }

    canvas.drawCircle(center, 9.dp.value, paint)
}
