package com.example.composegraphlibrary.barchart.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Paint

data class XAxisLineData(val linePoints: Pair<Offset, Offset>, val paint: Paint)

data class YAxisLineData(val linePoints: Pair<Offset, Offset>, val paint: Paint)

data class QuadrantLineData(val linePoints: Pair<Offset, Offset>, val paint: Paint)

data class QuadrantLinesData(val linePoints: List<QuadrantLineData>)

data class QuadrantYLineData(val linePoints: Pair<Offset, Offset>, val paint: Paint)

data class XLabel(val label: String, val point: Offset, val paint: Paint)

data class XLabels(val labels: List<XLabel>)

data class YLabel(val label: String, val point: Offset, val paint: Paint)

data class YLabels(val labels: List<YLabel>)

data class QuadrantRectData(val rect: Rect, val paint: Paint)

data class QuadrantRectsData(val quadrantRectsData: List<QuadrantRectData>)

