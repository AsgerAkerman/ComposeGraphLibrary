package com.example.composegraphlibrary.linegraph.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Paint

data class XAxisLineData(val linePoints: Pair<Offset, Offset>, val paint: Paint)

data class YAxisLineData(val linePoints: Pair<Offset, Offset>, val paint: Paint)

data class QuadrantLinesData(val linePoints: List<Pair<Offset, Offset>>, val paint: Paint)

data class QuadrantYLineData(val linePoints: Pair<Offset, Offset>, val paint: Paint)

data class XLabel(val label: String, val point: Offset, val paint: Paint)

data class XLabels(val labels: List<XLabel>)

data class YLabel(val label: String, val point: Offset, val paint: Paint)

data class YLabels(val labels: List<YLabel>)

data class QuadrantDataPoint(val linePoint: Pair<Offset, Offset>, val paint: Paint)

data class QuadrantDataPoints(val linePoints: List<QuadrantDataPoint>)
