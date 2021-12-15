package com.example.composegraphlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composegraphlibrary.barchart.BarChartRectCalculator.computeBarQuadrantRect
import com.example.composegraphlibrary.barchart.BarChartRectCalculator.computeBarXAxisRect
import com.example.composegraphlibrary.barchart.BarChartRectCalculator.computeBarYAxisRect
import com.example.composegraphlibrary.barchart.BarChartValues
import com.example.composegraphlibrary.barchart.BarQuadrantDrawer
import com.example.composegraphlibrary.barchart.BarXAxisDrawer
import com.example.composegraphlibrary.barchart.BarYAxisDrawer
import com.example.composegraphlibrary.linegraph.data.LineGraphRectCalculator.computeQuadrantRect
import com.example.composegraphlibrary.linegraph.data.LineGraphRectCalculator.computeXAxisRect
import com.example.composegraphlibrary.linegraph.data.LineGraphRectCalculator.computeYAxisRect
import com.example.composegraphlibrary.linegraph.data.LineGraphValues
import com.example.composegraphlibrary.linegraph.ui.QuadrantDrawer
import com.example.composegraphlibrary.linegraph.ui.XAxisDrawer
import com.example.composegraphlibrary.linegraph.ui.YAxisDrawer
import com.example.composegraphlibrary.piechart.ui.PieChartDrawer
import com.example.composegraphlibrary.piechart.data.PieChartRectCalculator.computeLabelRect
import com.example.composegraphlibrary.piechart.data.PieChartRectCalculator.computePieRect
import com.example.composegraphlibrary.piechart.data.PieChartValues
import com.example.composegraphlibrary.ui.theme.ComposeGraphLibraryTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private var transactionDataLineGraph = mutableStateListOf<LineGraphValues.DataPoint>()
    private var transactionDataBarGraph = mutableStateListOf<BarChartValues.BarChartDataPoint>()
    private var transactionDataPieChart = mutableStateListOf<Pair<Float, String>>()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGraphLibraryTheme {
                Timber.plant(Timber.DebugTree())
                transactionDataLineGraph = fakeData() as SnapshotStateList<LineGraphValues.DataPoint>
                transactionDataPieChart = fakeDataPie() as SnapshotStateList<Pair<Float, String>>
                transactionDataBarGraph = fakeBarChartData() as SnapshotStateList<BarChartValues.BarChartDataPoint>
                // PieGraphComponent()
                // LineGraphComponent()
                 BarChartComponent()
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
    fun BarChartComponent() {
        val barChartValues = BarChartValues(transactionDataBarGraph)
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
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ) {

                val height = (size.height * 0.3f)
                val yAxisRect = computeBarYAxisRect(height, size)
                val xAxisRect = computeBarXAxisRect(height, yAxisRect.width, size)
                val barQuadrantRect = computeBarQuadrantRect(xAxisRect, yAxisRect, size)

                val barYAxisDrawer = BarYAxisDrawer(drawContext.canvas, yAxisRect, barChartValues)
                val barXAxisDrawer = BarXAxisDrawer(drawContext.canvas, xAxisRect, barChartValues)
                val barQuadrantDrawer =
                    BarQuadrantDrawer(drawContext.canvas, barQuadrantRect, barChartValues)

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

    @ExperimentalFoundationApi
    @Composable
    fun PieGraphComponent() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Current stock", style = MaterialTheme.typography.h2)
            val pieChartValues = PieChartValues(transactionDataPieChart)
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
                val labelRect = computeLabelRect(size)
                val pieRect = computePieRect(labelRect, size)
                val pieChartDrawer = PieChartDrawer(
                    pieChartValues,
                    drawContext.canvas,
                    pieRect,
                    labelRect,
                    transitionProgress.value
                )
                pieChartDrawer.drawPieChart()
                //pieChartDrawer.drawRect(size)
            }
            VerticalGridOfLabels(pieChartValues)
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
                .padding(10.dp)
        ) {
            animationTargetValue.value = 1f
            val lineGraphValues = LineGraphValues(transactionDataLineGraph)

            val height = (size.height * 0.3f)
            val yAxisRect = computeYAxisRect(height, size)
            val xAxisRect = computeXAxisRect(height, yAxisRect.width, size)
            val quadrantRect = computeQuadrantRect(xAxisRect, yAxisRect, size)

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

    private fun rng(): Int {
        return (400..1100).random()
    }

    private fun fakeData(): MutableList<LineGraphValues.DataPoint> {
        repeat(10) {
            val transactionAmount = rng()
            transactionDataLineGraph.add(
                LineGraphValues.DataPoint(
                    transactionAmount.toFloat(),
                    "${it + 1}"
                )
            )
        }
        return transactionDataLineGraph
    }

    private fun fakeDataPie(): MutableList<Pair<Float, String>> {
        repeat(5) {
            val transactionAmount = rng()
            transactionDataPieChart.add(Pair(transactionAmount.toFloat(), "Label $it"))
        }
        return transactionDataPieChart
    }

    private fun fakeBarChartData(): MutableList<BarChartValues.BarChartDataPoint> {
        val colorOne = randomColor
        val colorTwo = randomColor
        val colorThree = randomColor

        repeat(3) {
            transactionDataBarGraph.add(
                BarChartValues.BarChartDataPoint(
                    "Brand $it",
                    mutableListOf(
                        BarChartValues.Category("Shoes", rng().toFloat(), colorOne),
                        BarChartValues.Category("Hoodies", rng().toFloat(), colorTwo),
                        BarChartValues.Category("Jackets", rng().toFloat(), colorThree)
                    )
                )
            )
        }
        return transactionDataBarGraph
    }

    private val randomColor: Color
        get() {
            return Color((30..200).random(), (30..200).random(), (30..200).random())
        }
}
