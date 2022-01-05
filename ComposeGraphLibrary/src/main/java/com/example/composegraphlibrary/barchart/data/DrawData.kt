package com.example.composegraphlibrary.barchart.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Paint

data class LineData(val linePoints: Pair<Offset, Offset>, val paint: Paint)

data class LinesData(val linePoints: List<LineData>)

data class Label(val label: String, val point: Offset, val paint: Paint)

data class Labels(val labels: List<Label>)

data class QuadrantRectData(val rect: Rect, val paint: Paint)

data class QuadrantRectsData(val quadrantRectsData: List<QuadrantRectData>)
