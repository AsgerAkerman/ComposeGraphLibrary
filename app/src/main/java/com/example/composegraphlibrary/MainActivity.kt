package com.example.composegraphlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.composegraphlibrary.ui.drawing.XAxisDrawer
import com.example.composegraphlibrary.data.LineGraphValues
import com.example.composegraphlibrary.ui.drawing.CanvasRectCalculator.computeQuadrantRect
import com.example.composegraphlibrary.ui.drawing.CanvasRectCalculator.computeXAxisRect
import com.example.composegraphlibrary.ui.drawing.CanvasRectCalculator.computeYAxisRect
import com.example.composegraphlibrary.ui.drawing.QuadrantDrawer
import com.example.composegraphlibrary.ui.drawing.YAxisDrawer
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private var transactionData = mutableStateListOf<LineGraphValues.DataPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Timber.plant(Timber.DebugTree());
            transactionData = fakeData() as SnapshotStateList<LineGraphValues.DataPoint>
            GraphComponent()
        }
    }

    @Composable
    fun GraphComponent(
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val lineGraphValues = LineGraphValues(transactionData)

            val height = (3f / 2f) * (44.sp.toPx() + 2f)
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

            quadrantDrawer.drawDataPoints()
            quadrantDrawer.drawQuadrantLines()
            quadrantDrawer.drawYLine()
        }
    }

    private fun randomTransactionGenerator(): Int {
        return (100000..110000).random()
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
