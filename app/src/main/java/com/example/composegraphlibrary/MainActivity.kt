package com.example.composegraphlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composegraphlibrary.linegraph.data.LineGraphRectCalculator.computeQuadrantRect
import com.example.composegraphlibrary.linegraph.data.LineGraphRectCalculator.computeXAxisRect
import com.example.composegraphlibrary.linegraph.data.LineGraphRectCalculator.computeYAxisRect
import com.example.composegraphlibrary.linegraph.data.LineGraphValues
import com.example.composegraphlibrary.linegraph.ui.QuadrantDrawer
import com.example.composegraphlibrary.linegraph.ui.XAxisDrawer
import com.example.composegraphlibrary.linegraph.ui.YAxisDrawer
import com.example.composegraphlibrary.piechart.PieChartDrawer
import com.example.composegraphlibrary.piechart.PieChartRectCalculator.computeLabelRect
import com.example.composegraphlibrary.piechart.PieChartRectCalculator.computePieRect
import com.example.composegraphlibrary.piechart.PieChartValues
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private var transactionDataLineGraph = mutableStateListOf<LineGraphValues.DataPoint>()
    private var transactionDataPieChart = mutableStateListOf<Pair<Float, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Timber.plant(Timber.DebugTree())
            transactionDataLineGraph = fakeData() as SnapshotStateList<LineGraphValues.DataPoint>
            //transactionDataPieChart = fakeDataPie() as SnapshotStateList<Pair<Float, String>>
            //PieGraphComponent()
            LineGraphComponent()
        }
    }

    @Composable
    fun PieGraphComponent() {
        val pieChartValues = PieChartValues(transactionDataPieChart)

        val transitionProgress = remember(pieChartValues.listOfPieData) { Animatable(initialValue = 0f) }
        LaunchedEffect(pieChartValues.listOfPieData) {
            transitionProgress.animateTo(1f, animationSpec = tween(durationMillis = 1000))
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            val rect = computeLabelRect(size)
            val rectTwo = computePieRect(rect, size)
            val pieChartDrawer = PieChartDrawer(pieChartValues, drawContext.canvas, rectTwo, rect, transitionProgress.value)
            pieChartDrawer.drawPieChart()
            pieChartDrawer.drawLabels()
            pieChartDrawer.drawRect()
        }
    }

    @Composable
    fun LineGraphComponent() {
        val animationTargetValue = remember { mutableStateOf(0f) }
        val animatedFloatValue = animateFloatAsState(
            targetValue = animationTargetValue.value,
            animationSpec = tween(durationMillis = 1000),
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            animationTargetValue.value = 1f
            val lineGraphValues = LineGraphValues(transactionDataLineGraph)

            val height = (3f / 2f) * (44.sp.toPx())
            val yAxisRect = computeYAxisRect(height, drawContext.size)
            val xAxisRect = computeXAxisRect(height, yAxisRect.width, drawContext.size)
            val quadrantRect = computeQuadrantRect(xAxisRect, yAxisRect, drawContext.size)

            val xAxisDrawer = XAxisDrawer(xAxisRect, drawContext.canvas, lineGraphValues)
            val yAxisDrawer = YAxisDrawer(drawContext.canvas, yAxisRect, lineGraphValues)
            val quadrantDrawer = QuadrantDrawer(drawContext.canvas, quadrantRect, lineGraphValues)

            xAxisDrawer.drawXAxisLine()
            xAxisDrawer.drawLabels()

            yAxisDrawer.drawYAxisLine()
            yAxisDrawer.drawLabels()

            quadrantDrawer.drawDataPoints(animatedFloatValue.value)
            quadrantDrawer.drawQuadrantLines(animatedFloatValue.value)
            quadrantDrawer.drawYLine()
        }
    }

    private fun randomTransactionGenerator(): Int {
        return (20..100).random()
    }

    private fun fakeData(): MutableList<LineGraphValues.DataPoint> {
        repeat(20) {
            val transactionAmount = randomTransactionGenerator()
            transactionDataLineGraph.add(
                LineGraphValues.DataPoint(
                    transactionAmount.toFloat(),
                    it.toString()
                )
            )
        }
        return transactionDataLineGraph
    }

    private fun fakeDataPie(): MutableList<Pair<Float, String>> {
        repeat(5) {
            val transactionAmount = randomTransactionGenerator()
            transactionDataPieChart.add(Pair(transactionAmount.toFloat(), "Label $it"))
        }
        return transactionDataPieChart
    }
}
