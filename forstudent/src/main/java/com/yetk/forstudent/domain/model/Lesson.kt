package com.yetk.forstudent.domain.model

data class Lesson(
    val subject: String,
    val rooms: List<String>,
    val teachers: List<String>,
    val number: Int,
    val dayOfWeek: Int,
    val weekState: Int,
    val isCanceled: Boolean = false
) {
    fun startTime(bellSchedule: BellSchedule): String {
        if (bellSchedule.lessonsTime.isEmpty()) {
            return "##:##"
        }
        return bellSchedule.lessonsTime[this.number - 1].substring(0, 5)
    }

    fun endTime(bellSchedule: BellSchedule): String {
        if (bellSchedule.lessonsTime.isEmpty()) {
            return "##:##"
        }
        return bellSchedule.lessonsTime[this.number - 1].substring(8, 13)
    }
}
