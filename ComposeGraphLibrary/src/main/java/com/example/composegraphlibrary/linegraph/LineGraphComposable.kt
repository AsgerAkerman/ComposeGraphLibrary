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
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
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

            val xAxisLineData = LineGraphUtils.getXAxisLineData(xAxisRect, styleConfig)
            val yAxisLineData = LineGraphUtils.getYAxisLineData(yAxisRect, styleConfig)
            val quadrantLinesData = LineGraphUtils.getQuadrantLines(quadrantRect, styleConfig)
            val quadrantYLineData = LineGraphUtils.getQuadrantYLineData(quadrantRect, styleConfig)
            val xLabelData = LineGraphUtils.getXLabelData(data, xAxisRect)
            val yLabelData = LineGraphUtils.getYLabelData(yAxisRect, data)
            val quadrantDataPoints = LineGraphUtils.getQuadrantDataPoints(animatedFloatValue.value, quadrantRect, data, styleConfig)

            drawXAxisLine(xAxisLineData)
            drawYAxisLine(yAxisLineData)
            drawQuadrantLines(quadrantLinesData)
            drawQuadrantYLine(quadrantYLineData)
            drawXLabels(xLabelData)
            drawYLabels(yLabelData)
            drawDataPoints(quadrantDataPoints, animatedFloatValue.value, styleConfig)
        }
        Text(text = description, style = MaterialTheme.typography.body1)
    }
}

fun DrawScope.drawXAxisLine(lineData: XAxisLineData) {
    drawContext.canvas.drawLine(
        p1 = Offset(
            x = lineData.linePoints.first.x,
            y = lineData.linePoints.first.y
        ),
        p2 = Offset(
            x = lineData.linePoints.second.x,
            y = lineData.linePoints.second.y
        ),
        paint = lineData.paint
    )
}

fun DrawScope.drawYAxisLine(yAxisLineData: YAxisLineData) {
    drawContext.canvas.drawLine(
        p1 = Offset(
            x = yAxisLineData.linePoints.first.x,
            y = yAxisLineData.linePoints.first.y
        ),
        p2 = Offset(
            x = yAxisLineData.linePoints.second.x,
            y = yAxisLineData.linePoints.second.y
        ),
        paint = yAxisLineData.paint
    )
}

fun DrawScope.drawQuadrantLines(quadrantDataPoints: QuadrantDataPoints) {
    quadrantDataPoints.linePoints.forEach {
        drawContext.canvas.drawLine(
            p1 = Offset(
                x = it.linePoint.first.x,
                y = it.linePoint.first.y
            ),
            p2 = Offset(
                x = it.linePoint.second.x,
                y = it.linePoint.second.y
            ),
            paint = it.paint
        )
    }
}

fun DrawScope.drawQuadrantYLine(quadrantYLineData: QuadrantYLineData) {
    drawContext.canvas.drawLine(
        p1 = Offset(
            x = quadrantYLineData.linePoints.first.x,
            y = quadrantYLineData.linePoints.first.y
        ),
        p2 = Offset(
            x = quadrantYLineData.linePoints.second.x,
            y = quadrantYLineData.linePoints.second.y
        ),
        paint = quadrantYLineData.paint
    )
}

fun DrawScope.drawXLabels(xLabels: XLabels) {
    xLabels.labels.forEach {
        drawContext.canvas.nativeCanvas.drawText(
            it.label,
            it.point.x,
            it.point.y,
            it.paint.asFrameworkPaint()
        )
    }
}

fun DrawScope.drawYLabels(yLabels: YLabels) {
    yLabels.labels.forEach {
        drawContext.canvas.nativeCanvas.drawText(
            it.label,
            it.point.x,
            it.point.y,
            it.paint.asFrameworkPaint()
        )
    }
}

fun DrawScope.drawDataPoints(
    quadrantDataPoints: QuadrantDataPoints,
    progress: Float,
    styleConfig: LineChartStyleConfig
) {
    quadrantDataPoints.linePoints.forEach {
        drawContext.canvas.drawLine(
            p1 = it.linePoint.first,
            p2 = it.linePoint.second,
            paint = it.paint
        )

        drawPoint(
            canvas = drawContext.canvas,
            center = it.linePoint.first,
            progress = progress,
            styleConfig = styleConfig
        )
    }
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
