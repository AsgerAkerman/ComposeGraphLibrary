package com.example.composegraphlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.barchart.BarChartRectCalculator.computeBarQuadrantRect
import com.example.composegraphlibrary.barchart.BarChartRectCalculator.computeBarXAxisRect
import com.example.composegraphlibrary.barchart.BarChartRectCalculator.computeBarYAxisRect
import com.example.composegraphlibrary.barchart.BarChartValues
import com.example.composegraphlibrary.barchart.BarQuadrantDrawer
import com.example.composegraphlibrary.barchart.BarXAxisDrawer
import com.example.composegraphlibrary.barchart.BarYAxisDrawer
import com.example.composegraphlibrary.linegraph.data.LineGraphValues
import com.example.composegraphlibrary.ui.theme.ComposeGraphLibraryTheme

class MainActivity : ComponentActivity() {

    private var transactionDataLineGraph = mutableStateListOf<LineGraphValues.DataPoint>()
    private var transactionDataBarGraph = mutableStateListOf<BarChartValues.BarChartDataPoint>()
    private var transactionDataPieChart = mutableStateListOf<Pair<Float, String>>()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGraphLibraryTheme {
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                }
            }
        }
    }

}
