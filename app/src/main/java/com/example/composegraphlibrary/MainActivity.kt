package com.example.composegraphlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composegraphlibrary.linegraph.ui.XAxisDrawer
import com.example.composegraphlibrary.linegraph.data.LineGraphValues
import com.example.composegraphlibrary.linegraph.data.CanvasRectCalculator.computeQuadrantRect
import com.example.composegraphlibrary.linegraph.data.CanvasRectCalculator.computeXAxisRect
import com.example.composegraphlibrary.linegraph.data.CanvasRectCalculator.computeYAxisRect
import com.example.composegraphlibrary.linegraph.ui.QuadrantDrawer
import com.example.composegraphlibrary.linegraph.ui.YAxisDrawer
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private var transactionData = mutableStateListOf<LineGraphValues.DataPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Timber.plant(Timber.DebugTree())
            transactionData = fakeData() as SnapshotStateList<LineGraphValues.DataPoint>
            GraphComponent()
        }
    }

    @Composable
    fun GraphComponent(
    ) {
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
            val lineGraphValues = LineGraphValues(transactionData)

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
            quadrantDrawer.drawQuadrantLines()
            quadrantDrawer.drawYLine()
        }
    }

    private fun randomTransactionGenerator(): Int {
        return (11000..12000).random()
    }

    private fun fakeData(): MutableList<LineGraphValues.DataPoint> {
        repeat(20) {
            val transactionAmount = randomTransactionGenerator()
            transactionData.add(
                LineGraphValues.DataPoint(
                    transactionAmount.toFloat(),
                    it.toString()
                )
            )
        }
        return transactionData
    }
}
