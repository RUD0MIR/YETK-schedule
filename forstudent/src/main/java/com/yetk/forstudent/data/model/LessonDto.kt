package com.yetk.forstudent.data.model

data class LessonDto(
    val subject: String = "",
    val rooms: List<String> = emptyList(),
    val teachers: List<String> = emptyList(),
    val number: Int = 1,
    val dayOfWeek: Int = 0,
    val weekState: Int = 0,
    val isCanceled: Boolean = false
)
