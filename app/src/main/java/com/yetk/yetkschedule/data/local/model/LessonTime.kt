package com.example.lessonsschedulemanagerv2.data.local.model

data class LessonTime(
    val id: Int,
    val number: Int,
    val startTime: String,
    val endTime: String
) {
    fun getTimeRange(): String {
        return "$startTime - $endTime"
    }
}