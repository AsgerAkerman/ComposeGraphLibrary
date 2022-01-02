package com.example.composegraphlibrary.piechart.data

class PieChartUtils(
    val listOfSlices: List<Slice>
) {

    private val totalValue: Float
        get() {
            var total = 0f
            listOfSlices.forEach { total += it.value }
            return total
        }

    fun calculateAngles(
        sliceSize: Float,
        progress: Float
    ): Float {
        return 360.0f * (sliceSize * progress) / totalValue
    }
}
