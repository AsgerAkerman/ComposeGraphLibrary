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
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.linegraph.data.*
import com.example.composegraphlibrary.linegraph.data.LineGraphUtils
import com.example.composegraphlibrary.linegraph.ui.*

@Composable
fun LineChartComposable(
    data: List<LineChartDataPoint>,
    description: String,
    unit: String,
    styleConfig: LineChartStyleConfig
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 20.dp)) {
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

            val yAxisRect = LineGraphRectCalculator.computeYAxisRect(size)
            val xAxisRect = LineGraphRectCalculator.computeXAxisRect(yAxisRect, size)
            val quadrantRect = LineGraphRectCalculator.computeQuadrantRect(xAxisRect, yAxisRect, size)

            val xAxisLineData = LineGraphUtils.getXAxisLineData(xAxisRect, styleConfig)
            val yAxisLineData = LineGraphUtils.getYAxisLineData(yAxisRect, styleConfig)
            val quadrantLinesData = LineGraphUtils.getQuadrantLines(quadrantRect, styleConfig)
            val quadrantYLineData = LineGraphUtils.getQuadrantYLineData(quadrantRect, styleConfig)
            val xLabelData = LineGraphUtils.getXLabelData(data, xAxisRect)
            val yLabelData = LineGraphUtils.getYLabelData(yAxisRect, data)
            val yUnitLabel = LineGraphUtils.getUnitLabelData(yAxisRect, unit)
            val quadrantDataPoints = LineGraphUtils.getQuadrantDataPoints(animatedFloatValue.value, quadrantRect, data, styleConfig)

            drawXAxisLine(xAxisLineData)
            drawYAxisLine(yAxisLineData)
            drawQuadrantLines(quadrantLinesData)
            drawQuadrantYLine(quadrantYLineData)
            drawXLabels(xLabelData)
            drawYLabels(yLabelData)
            drawYUnit(yUnitLabel)
            drawDataPoints(quadrantDataPoints, animatedFloatValue.value, styleConfig)
        }
        Text(text = description, style = MaterialTheme.typography.body1)
    }
}
