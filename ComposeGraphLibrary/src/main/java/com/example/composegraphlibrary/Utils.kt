package com.example.composegraphlibrary

import androidx.compose.ui.graphics.Paint
import kotlin.math.ln
import kotlin.math.pow

object Utils {
    fun setTextSizeForWidth(
        paint: Paint,
        desiredWidth: Float,
        text: String,
        isXLabel: Boolean
    ) {
        val testTextSize = 38f
        val bounds = android.graphics.Rect()
        with(paint.asFrameworkPaint()) {
            textSize = testTextSize
            getTextBounds(text, 0, text.length, bounds)
            textSize = (testTextSize * desiredWidth / bounds.width()) * 0.75f
            if (isXLabel) {
                textSize /= 2f
            }
            getTextBounds(text, 0, text.length, bounds)

            if (paint.asFrameworkPaint().textSize > testTextSize) {
                textSize = testTextSize
            }
        }
    }

    // https://stackoverflow.com/questions/41859525/how-to-go-about-formatting-1200-to-1-2k-in-android-studio
    fun getFormatedNumber(count: Long): String {
        if (count < 10000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }
}
