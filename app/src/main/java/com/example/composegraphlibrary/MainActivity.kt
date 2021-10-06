package com.example.composegraphlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.composegraphlibrary.ui.drawing.DrawXAxis
import com.example.composegraphlibrary.ui.drawing.DrawYAxis

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val drawXAxis = DrawXAxis(4f, Color.Black, 30f)
        val drawYAxis = DrawYAxis(4f, Color.Black, 30f)
        setContent {
            GraphComponent(drawXAxis, drawYAxis)
        }
    }

    @Composable
    fun GraphComponent(
        drawXAxis: DrawXAxis,
        drawYAxis: DrawYAxis
    ) {
        Canvas(modifier = Modifier.fillMaxWidth(fraction = 1f)
            .fillMaxHeight(fraction = 0.6f)) {
            drawXAxis.drawXAxisLine(this)
            drawYAxis.drawYAxisLine(this)
        }
    }
}
