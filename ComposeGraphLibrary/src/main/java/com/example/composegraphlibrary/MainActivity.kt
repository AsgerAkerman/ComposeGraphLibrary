package com.example.composegraphlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composegraphlibrary.barchart.data.BarChartValues
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
