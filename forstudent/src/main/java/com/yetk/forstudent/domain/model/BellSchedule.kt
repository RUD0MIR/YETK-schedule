package com.yetk.forstudent.domain.model

data class BellSchedule(
    val lessonDurationMin: Int = 0,
    val lessonsTime: List<String> = emptyList()
)
