package com.example.composegraphlibrary

import androidx.compose.ui.graphics.Paint
import com.example.composegraphlibrary.linegraph.data.GraphConstants
import kotlin.math.ln
import kotlin.math.pow

object Utils {
    fun setTextSizeForWidth(
        paint: Paint,
        desiredWidth: Float,
        text: String,
        isXLabel: Boolean
    ) {
        val testTextSize = 48f
        val bounds = android.graphics.Rect()
        with(paint.asFrameworkPaint()) {
            textSize = testTextSize
            getTextBounds(text, 0, text.length, bounds)
            textSize = (testTextSize * desiredWidth / bounds.width())
            if (isXLabel) {
                textSize /= 2f
            }
            getTextBounds(text, 0, text.length, bounds)
        }
    }

    // https://stackoverflow.com/questions/41859525/how-to-go-about-formatting-1200-to-1-2k-in-android-studio
    fun getFormatedNumber(count: Long): String {
        if (count < 10000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }

    fun getYlabels(upperYValue: Float, lowerYValue: Float, yLabelCount: Float): MutableList<String> {
        val yValues = mutableListOf<String>()
        repeat(yLabelCount.toInt() + 1) {
            val valueFromInterval = ((upperYValue - lowerYValue) * ((1f / GraphConstants.NUMBER_OF_Y_LABELS) * (GraphConstants.NUMBER_OF_Y_LABELS - it))) + lowerYValue
            yValues.add(getFormatedNumber(valueFromInterval.toLong()))
        }

        return yValues
    }
}
