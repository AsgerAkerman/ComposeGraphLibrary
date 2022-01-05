package com.example.composegraphlibrary.barchart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.barchart.data.BarChartDataPoint
import com.example.composegraphlibrary.barchart.data.BarChartRectCalculator
import com.example.composegraphlibrary.barchart.data.BarChartStyleConfig
import com.example.composegraphlibrary.barchart.data.BarChartUtils
import com.example.composegraphlibrary.barchart.ui.*

@Composable
fun BarChartComponent(data: List<BarChartDataPoint>, styleConfig: BarChartStyleConfig) {
    Column {
        Row(
            Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            data.first().categories.forEach {
                ColorLabel(text = it.name, color = it.color)
            }
        }

        val transitionProgress = remember(data) { Animatable(initialValue = 0f) }
        LaunchedEffect(data) {
            transitionProgress.animateTo(1f, animationSpec = tween(durationMillis = 1000))
        }
        Canvas(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(10.dp)
        ) {


            val yAxisRect = BarChartRectCalculator.computeBarYAxisRect(size)
            val xAxisRect = BarChartRectCalculator.computeBarXAxisRect(yAxisRect.width, size)
            val barQuadrantRect = BarChartRectCalculator.computeBarQuadrantRect(xAxisRect, yAxisRect, size)

            val xAxisLineData = BarChartUtils.getXAxisLineData(xAxisRect, styleConfig)
            val yAxisLineData = BarChartUtils.getYAxisLineData(yAxisRect, styleConfig)
            val quadrantLinesData = BarChartUtils.getQuadrantLines(barQuadrantRect, styleConfig)
            val quadrantYLineData = BarChartUtils.getQuadrantYLineData(barQuadrantRect, styleConfig)
            val xLabelData = BarChartUtils.getXLabelData(data, xAxisRect)
            val yLabelData = BarChartUtils.getYLabelData(yAxisRect, data)
            val quadrantDataPoints = BarChartUtils.getQuadrantRectsData(transitionProgress.value, data, barQuadrantRect)

            drawXAxisLine(xAxisLineData)
            drawYAxisLine(yAxisLineData)
            drawQuadrantLines(quadrantLinesData)
            drawQuadrantYLine(quadrantYLineData)
            drawXLabels(xLabelData)
            drawYLabels(yLabelData)
            drawBarCharts(quadrantDataPoints)
        }
    }
}

@Composable
fun ColorLabel(text: String, color: Color) {
    Row(
        modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CutCornerShape(3.dp))
                .background(color = color)
        )
    }
}
