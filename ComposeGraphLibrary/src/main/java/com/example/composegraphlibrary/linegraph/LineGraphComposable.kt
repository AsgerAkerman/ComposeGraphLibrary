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
import com.example.composegraphlibrary.linegraph.data.LineGraphDataPoint
import com.example.composegraphlibrary.linegraph.data.LineGraphRectCalculator
import com.example.composegraphlibrary.linegraph.data.LineGraphUtils
import com.example.composegraphlibrary.linegraph.ui.QuadrantDrawer
import com.example.composegraphlibrary.linegraph.ui.XAxisDrawer
import com.example.composegraphlibrary.linegraph.ui.YAxisDrawer

@Composable
fun LineChartComposable(data: List<LineGraphDataPoint>, description: String) {
    val lineGraphValues = LineGraphUtils(data)
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

            val xAxisDrawer = XAxisDrawer(xAxisRect, drawContext.canvas, lineGraphValues)
            val yAxisDrawer = YAxisDrawer(drawContext.canvas, yAxisRect, lineGraphValues)
            val quadrantDrawer = QuadrantDrawer(drawContext.canvas, quadrantRect, lineGraphValues)

            xAxisDrawer.drawXAxisLine()
            xAxisDrawer.drawLabels()

            yAxisDrawer.drawYAxisLine()
            yAxisDrawer.drawLabels()

            quadrantDrawer.drawDataPoints(animatedFloatValue.value)
            quadrantDrawer.drawQuadrantLines()
            quadrantDrawer.drawYLine()
        }
        Text(text = description, style = MaterialTheme.typography.body1)
    }
}
