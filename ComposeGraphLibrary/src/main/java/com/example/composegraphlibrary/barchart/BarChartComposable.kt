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

@Composable
fun BarChartComponent(data: List<BarChartValues.BarChartDataPoint>) {
    val barChartValues = BarChartValues(data)
    Column {
        Row(
            Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            barChartValues.listOfData.first().categories.forEach {
                ColorLabel(text = it.name, color = it.color)
            }
        }

        val transitionProgress = remember(barChartValues.listOfData) { Animatable(initialValue = 0f) }
        LaunchedEffect(barChartValues.listOfData) {
            transitionProgress.animateTo(1f, animationSpec = tween(durationMillis = 1000))
        }
        Canvas(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(horizontal = 10.dp)
        ) {

            val height = (size.height * 0.3f)
            val yAxisRect = BarChartRectCalculator.computeBarYAxisRect(height, size)
            val xAxisRect = BarChartRectCalculator.computeBarXAxisRect(height, yAxisRect.width, size)
            val barQuadrantRect = BarChartRectCalculator.computeBarQuadrantRect(xAxisRect, yAxisRect, size)

            val barYAxisDrawer = BarYAxisDrawer(drawContext.canvas, yAxisRect, barChartValues)
            val barXAxisDrawer = BarXAxisDrawer(drawContext.canvas, xAxisRect, barChartValues)
            val barQuadrantDrawer = BarQuadrantDrawer(drawContext.canvas, barQuadrantRect, barChartValues)

            barYAxisDrawer.drawYAxisLine()
            barYAxisDrawer.drawLabels()
            barXAxisDrawer.drawXAxisLine()
            barXAxisDrawer.drawLabels()

            barQuadrantDrawer.drawQuadrantLines()
            barQuadrantDrawer.drawYLine()
            barQuadrantDrawer.drawBarCharts(transitionProgress.value)
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
