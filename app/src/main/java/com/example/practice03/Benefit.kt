package com.example.practice03

import java.text.SimpleDateFormat
import java.util.*

data class Benefit(
    val title: String,
    val documentId: String,
    val period: String,
    val imageResId: Int,
    val type: String,
    var isFavorite: Boolean = false
) {
    fun getDday(): Int? {
        val dateFormat = SimpleDateFormat("yy.M.d", Locale.getDefault())
        val periodParts = period.split("~")

        try {
            val endDate = periodParts[1].trim().takeIf { it.isNotEmpty() }?.let {
                val endDatePlusOne = it.split(".")
                val year = "20" + endDatePlusOne[0]
                val month = endDatePlusOne[1]
                val day = endDatePlusOne[2]
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year.toInt())
                calendar.set(Calendar.MONTH, month.toInt() - 1)
                calendar.set(Calendar.DAY_OF_MONTH, day.toInt() + 1) // 신청 마감일 다음 날로 설정
                calendar.time
            }
            val startDate = periodParts[0].trim().takeIf { it.isNotEmpty() }?.let {
                dateFormat.parse("20$it")
            }
            val currentDate = Date()

            return when {
                endDate != null && currentDate.before(endDate) -> {
                    val diff = endDate.time - currentDate.time
                    (diff / (1000 * 60 * 60 * 24)).toInt()
                }
                startDate != null && currentDate.before(startDate) -> {
                    val diff = startDate.time - currentDate.time
                    (diff / (1000 * 60 * 60 * 24)).toInt()
                }
                else -> null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}