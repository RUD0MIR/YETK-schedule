package com.yetk.model


data class Lesson(
    val subject: String = "",
    val rooms: List<String> = emptyList(),
    val teachers: List<String> = emptyList(),
    val number: Int = 1,
    val dayOfWeek: Int = 0,
    val weekState: Int = 0,
    val isCanceled: Boolean = false
    ) {
    fun startTime(bellSchedule: BellSchedule): String {
        if(bellSchedule.lessons_time.isEmpty()) {
            return "##:##"
        }
        return bellSchedule.lessons_time[this.number - 1].substring(0, 5)
    }

    fun endTime(bellSchedule: BellSchedule): String {
        if(bellSchedule.lessons_time.isEmpty()) {
            return "##:##"
        }
        return bellSchedule.lessons_time[this.number - 1].substring(8, 13)

    }
}