package com.example.composegraphlibrary.piechart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.piechart.data.PieChartRectCalculator
import com.example.composegraphlibrary.piechart.data.PieChartValues
import com.example.composegraphlibrary.piechart.ui.PieChartDrawer

@ExperimentalFoundationApi
@Composable
fun PieGraphComponent(data: List<Pair<Float, String>>) {
    val pieChartValues = PieChartValues(data)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Current stock", style = MaterialTheme.typography.h2)
        val transitionProgress =
            remember(pieChartValues.listOfPieData) { Animatable(initialValue = 0f) }
        LaunchedEffect(pieChartValues.listOfPieData) {
            transitionProgress.animateTo(1f, animationSpec = tween(durationMillis = 1000))
        }
        Canvas(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(10.dp)
        ) {
            val pieRect = PieChartRectCalculator.computePieRect(size)
            val pieChartDrawer = PieChartDrawer(
                pieChartValues,
                drawContext.canvas,
                pieRect,
                transitionProgress.value
            )
            pieChartDrawer.drawPieChart()
            // pieChartDrawer.drawRect(size)
        }
        VerticalGridOfLabels(pieChartValues)
    }
}

@ExperimentalFoundationApi
@Composable
fun VerticalGridOfLabels(
    data: PieChartValues
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(data.listOfPieData) { item ->
            PieChartLabelRow(text = item.label, color = item.color, value = item.value.toString())
        }
    }
}

@Composable
fun PieChartLabelRow(text: String, color: Color, value: String) {
    Column(
        modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorLabel(text = text, color = color)
        Text(text = value, style = MaterialTheme.typography.subtitle1)
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
