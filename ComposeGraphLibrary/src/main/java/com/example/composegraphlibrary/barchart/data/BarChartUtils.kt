package com.example.composegraphlibrary.barchart.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import com.example.composegraphlibrary.Utils
import com.example.composegraphlibrary.linegraph.data.GraphConstants
import com.example.composegraphlibrary.linegraph.data.GraphConstants.NUMBER_OF_Y_LABELS
import kotlin.math.absoluteValue

object BarChartUtils{
    private fun getUpperLowerValues(listOfData: List<BarChartDataPoint>): Pair<Float, Float> {
        var max = Float.MIN_VALUE
        var min = Float.MAX_VALUE

        listOfData.forEachIndexed { _, dataObject ->
            val maxCurrentCategoryValue =
                dataObject.categories.maxByOrNull { it.value }?.value?.absoluteValue ?: 0f
            val minCurrentCategoryValue =
                dataObject.categories.minByOrNull { it.value }?.value?.absoluteValue ?: 0f

            if (max < maxCurrentCategoryValue) {
                max = maxCurrentCategoryValue
            }

            if (min > minCurrentCategoryValue) {
                min = minCurrentCategoryValue
            }
        }

        val upperYValue: Float = min * (GraphConstants.DATASET_MAX_VALUE_PADDING)
        val lowerYValue: Float = max * (GraphConstants.DATASET_MIN_VALUE_PADDING)

        return upperYValue to lowerYValue
    }

    private fun calculateYLabelValues(data: List<BarChartDataPoint>): MutableList<String> {
        val upperLowerValues = getUpperLowerValues(data)
        val yValues = mutableListOf<String>()
        repeat(NUMBER_OF_Y_LABELS.toInt() + 1) {
            val valueFromInterval =
                ((upperLowerValues.first - upperLowerValues.second) * ((1f / NUMBER_OF_Y_LABELS) * (NUMBER_OF_Y_LABELS - it))) + upperLowerValues.second
            yValues.add(Utils.getFormatedNumber(valueFromInterval.toLong()))
        }

        return yValues
    }

    fun getYLabelData(yAxisRect: Rect, data: List<BarChartDataPoint>): YLabels {
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

    fun getYAxisLineData(yAxisRect: Rect, styleConfig: BarChartStyleConfig): YAxisLineData {
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

    private fun calculateXLabelDataPoints(
        quadrantRect: Rect,
        data: List<BarChartDataPoint>
    ): List<Float> {
        val listOfDataPoints = mutableListOf<Float>()
        data.forEachIndexed { index, _ ->
            val xCoordinate = (((quadrantRect.width / (data.size - 1f) * (index))))
            listOfDataPoints.add(xCoordinate + quadrantRect.left)
        }

        return listOfDataPoints
    }

    private fun getYPoint(quadrantRect: Rect, value: Float, data: List<BarChartDataPoint>): Float {
        val upperLowerValues = getUpperLowerValues(data)
        return ((upperLowerValues.first - value) / (upperLowerValues.first - upperLowerValues.second)) * quadrantRect.height
    }


    fun getXAxisLineData(xAxisRect: Rect, styleConfig: BarChartStyleConfig): XAxisLineData {
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

    fun getXLabelData(
        data: List<BarChartDataPoint>,
        xAxisRect: Rect,
    ): XLabels {
        val paint = Paint()
        val tempList = mutableListOf<XLabel>()
        val labelTextWidth = xAxisRect.width * (1f / (data.size))
        val longestString = data.maxOf { it.xLabel }.toString()
        Utils.setTextSizeForWidth(paint, labelTextWidth, longestString, true)
        val yPaddingText = paint.asFrameworkPaint().textSize * 1.5f

        val xCoordinate = calculateXLabelDataPoints(xAxisRect, data)
        data.forEachIndexed { index, dataPoint ->
            tempList.add(
                XLabel(
                    dataPoint.xLabel,
                    Offset(
                        xCoordinate[index], xAxisRect.top + yPaddingText
                    ),
                    paint
                )
            )
        }
        return XLabels(tempList)
    }

    fun getQuadrantRectsData(
        progress: Float,
        data: List<BarChartDataPoint>,
        quadrantRect: Rect
    ): QuadrantRectsData {
        val tempList = mutableListOf<QuadrantRectData>()

        data.forEachIndexed { brandIndex, brandObject ->
            val categories = brandObject.categories

            val axisSeparator = (quadrantRect.width * ((1f / data.size)))

            val xStart = (quadrantRect.left) + (axisSeparator * brandIndex)
            val xStartForCalc = (axisSeparator * brandIndex)

            val xEnd = axisSeparator * (brandIndex + 1f)
            val xDelta = ((xEnd - xStartForCalc) / categories.size) * 0.90f

            categories.forEachIndexed { index, category ->
                tempList.add(QuadrantRectData(
                    Rect(
                        xStart + (xDelta * index),
                        quadrantRect.bottom,
                        xStart + (xDelta * (index + 1f)),
                        getYPoint(quadrantRect, category.value, data) * progress
                    ),
                    Paint().apply {
                        color = category.color
                    }
                )
                )
            }
        }
        return QuadrantRectsData(tempList)
    }

    fun getQuadrantLines(quadrantRect: Rect, styleConfig: BarChartStyleConfig): QuadrantLinesData {
        val paint = Paint().apply {
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            strokeWidth = styleConfig.quadrantLineWidth
            color = styleConfig.quadrantDottedLineColor
        }

        val listOfData = mutableListOf<QuadrantLineData>()
        repeat(NUMBER_OF_Y_LABELS.toInt()) {
            var y = quadrantRect.bottom * (it / NUMBER_OF_Y_LABELS)
            if (it == 0) {
                y = (quadrantRect.bottom * 0f)
            }
            listOfData.add(
                QuadrantLineData(
                    Pair(
                        Offset(quadrantRect.left, y),
                        Offset(quadrantRect.right, y)
                    ),
                    paint
                )
            )
        }
        return QuadrantLinesData(listOfData)
    }

    fun getQuadrantYLineData(
        quadrantRect: Rect,
        styleConfig: BarChartStyleConfig
    ): QuadrantYLineData {
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
}