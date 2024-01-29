package com.yetk.yetkschedule.data.remote.model

import com.yetk.yetkschedule.other.LessonWeekState


data class Lesson(
    val subject: String = "",
    val rooms: List<String> = emptyList(),
    val teachers: List<String> = emptyList(),
    val number: Int = 1,
    val dayOfWeek: Int = 0,
    val weekState: Int = 0,
    val isCanceled: Boolean = false
    )