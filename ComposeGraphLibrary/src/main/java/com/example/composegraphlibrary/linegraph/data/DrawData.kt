package com.example.composegraphlibrary.linegraph.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Paint

data class LineData(val linePoints: Pair<Offset, Offset>, val paint: Paint)

data class Label(val label: String, val point: Offset, val paint: Paint)

data class Labels(val labels: List<Label>)

data class CirclePointData(val point: Offset, val paint: Paint)

data class CirclePointsData(val list: List<CirclePointData>)

data class QuadrantDataPoints(val linePoints: List<LineData>)
