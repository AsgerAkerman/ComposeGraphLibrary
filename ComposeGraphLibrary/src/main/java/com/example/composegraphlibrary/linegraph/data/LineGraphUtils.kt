package com.example.composegraphlibrary.linegraph.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_MAX_VALUE_PADDING
import com.example.composegraphlibrary.linegraph.data.GraphConstants.DATASET_MIN_VALUE_PADDING
import com.example.composegraphlibrary.linegraph.data.GraphConstants.NUMBER_OF_Y_LABELS

object LineGraphUtils {
    private fun getUpperLowerValues(listOfData: List<LineChartDataPoint>): Pair<Float, Float> {
        val minValue = listOfData.minByOrNull { it.yValue }?.yValue ?: 0f
        val maxValue = listOfData.maxByOrNull { it.yValue }?.yValue ?: 0f

        val lowerValue: Float = minValue * DATASET_MIN_VALUE_PADDING
        val upperValue: Float = maxValue * DATASET_MAX_VALUE_PADDING
        return upperValue to lowerValue
    }

    private fun calculateDataPoints(
        quadrantRect: Rect,
        data: List<LineChartDataPoint>
    ): List<Pair<Offset, LineChartDataPoint>> {
        val listOfDataPoints = mutableListOf<Pair<Offset, LineChartDataPoint>>()
        val upperLowerValues = getUpperLowerValues(data)
        data.forEachIndexed { index, point ->
            val yCoordinate = (((upperLowerValues.first - point.yValue) / (upperLowerValues.first - upperLowerValues.second))) * quadrantRect.height
            val xCoordinate = (((quadrantRect.width / (data.size - 1f) * (index))))
            listOfDataPoints.add(Pair(Offset(xCoordinate + quadrantRect.left, yCoordinate), point))
        }

        return listOfDataPoints
    }

    private fun calculateYLabelValues(data: List<LineChartDataPoint>): MutableList<String> {
        val upperLowerValues = getUpperLowerValues(data)
        val yValues = mutableListOf<String>()
        repeat(NUMBER_OF_Y_LABELS.toInt() + 1) {
            val valueFromInterval = ((upperLowerValues.first - upperLowerValues.second) * ((1f / NUMBER_OF_Y_LABELS) * (NUMBER_OF_Y_LABELS - it))) + upperLowerValues.second
            yValues.add(Utils.getFormatedNumber(valueFromInterval.toLong()))
        }

        return yValues
    }

    fun getXAxisLineData(xAxisRect: Rect, styleConfig: LineChartStyleConfig): XAxisLineData {
        val paint = Paint().apply {
            color = styleConfig.xAxisLineColor
            strokeWidth = styleConfig.xAxisLineWidth
        }
        val yPoint = xAxisRect.top + (styleConfig.xAxisLineWidth / 2f)
        return XAxisLineData(
            Pair(
                Offset(
                    x = xAxisRect.left - 5f,
                    y = yPoint
                ),
                Offset(
                    x = xAxisRect.right,
                    y = yPoint
                )
            ),
            paint
        )
    }

    fun getYAxisLineData(yAxisRect: Rect, styleConfig: LineChartStyleConfig): YAxisLineData {
        val paint = Paint().apply {
            color = styleConfig.yAxisLineColor
            strokeWidth = styleConfig.yAxisLineWidth
        }
        val x = yAxisRect.right - styleConfig.yAxisLineWidth
        return YAxisLineData(
            Pair(
                Offset(
                    x = x,
                    y = yAxisRect.top
                ),
                Offset(
                    x = x,
                    y = yAxisRect.bottom
                )
            ),
            paint
        )
    }

    fun getQuadrantLines(quadrantRect: Rect, styleConfig: LineChartStyleConfig): QuadrantDataPoints {
        val paint = Paint().apply {
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            strokeWidth = styleConfig.quadrantLineWidth
            color = styleConfig.quadrantDottedLineColor
        }

        val listOfData = mutableListOf<QuadrantDataPoint>()
        repeat(NUMBER_OF_Y_LABELS.toInt()) {
            var y = quadrantRect.bottom * (it / NUMBER_OF_Y_LABELS)
            if (it == 0) {
                y = (quadrantRect.bottom * 0f)
            }
            listOfData.add(
                QuadrantDataPoint(
                    Pair(
                        Offset(quadrantRect.left, y),
                        Offset(quadrantRect.right, y)
                    ),
                    paint
                )
            )
        }
        return QuadrantDataPoints(listOfData)
    }

    fun getQuadrantYLineData(quadrantRect: Rect, styleConfig: LineChartStyleConfig): QuadrantYLineData {
        val paint = Paint().apply {
            color = styleConfig.quadrantYLineColor
            strokeWidth = styleConfig.quadrantLineWidth
        }
        val x = quadrantRect.right
        return QuadrantYLineData(
            Pair(
                Offset(
                    x = x,
                    y = quadrantRect.top
                ),
                Offset(
                    x = x,
                    y = quadrantRect.bottom
                )
            ),
            paint
        )
    }

    fun getXLabelData(
        data: List<LineChartDataPoint>,
        xAxisRect: Rect,
    ): XLabels {
        val paint = Paint()
        val tempList = mutableListOf<XLabel>()
        val labelTextWidth = xAxisRect.width * (1f / (data.size))
        val longestString = data.maxOf { it.xLabel }.toString()
        Utils.setTextSizeForWidth(paint, labelTextWidth, longestString, true)
        val yPaddingText = paint.asFrameworkPaint().textSize * 1.5f

        val dataPoints = calculateDataPoints(xAxisRect, data)
        dataPoints.forEachIndexed { index, dataPoint ->
            tempList.add(
                XLabel(
                    data[index].xLabel.toString(),
                    Offset(
                        dataPoint.first.x - 20f, xAxisRect.top + yPaddingText
                    ),
                    paint
                )
            )
        }
        return XLabels(tempList)
    }

    fun getYLabelData(yAxisRect: Rect, data: List<LineChartDataPoint>): YLabels {
        val paint = Paint()
        val tempList = mutableListOf<YLabel>()
        val labelValues = calculateYLabelValues(data)
        val longestString = labelValues.maxOf { it.toFloat() }.toString()
        Utils.setTextSizeForWidth(paint, yAxisRect.width, longestString, false)

        labelValues.forEachIndexed { index, label ->
            val x = yAxisRect.left
            var y = yAxisRect.bottom * ((index) / NUMBER_OF_Y_LABELS)
            if (index == 0) {
                y = 0f
            }
            tempList.add(YLabel(label, Offset(x, y), paint))
        }
        return YLabels(tempList)
    }

    fun getQuadrantDataPoints(
        progress: Float,
        quadrantRect: Rect,
        data: List<LineChartDataPoint>,
        styleConfig: LineChartStyleConfig
    ): QuadrantDataPoints {
        val paint = Paint().apply {
            color = styleConfig.quadrantPathLineColor
            style = PaintingStyle.Stroke
            strokeWidth = styleConfig.quadrantLineWidth
        }
        val tempList = mutableListOf<QuadrantDataPoint>()
        var previousPointLocation: Offset? = null

        calculateDataPoints(quadrantRect, data).forEachIndexed { index, pair ->
            if (index > 0) {
                tempList.add(
                    QuadrantDataPoint(
                        Pair(
                            previousPointLocation!!,
                            Offset(
                                x = (pair.first.x - previousPointLocation!!.x) * progress + previousPointLocation!!.x,
                                y = (pair.first.y - previousPointLocation!!.y) * progress + previousPointLocation!!.y
                            )
                        ),
                        paint = paint
                    )
                )
            }
            previousPointLocation = pair.first
        }
        return QuadrantDataPoints(tempList)
    }
}
