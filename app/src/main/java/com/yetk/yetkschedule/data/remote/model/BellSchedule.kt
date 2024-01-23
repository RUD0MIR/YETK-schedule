package com.yetk.yetkschedule.data.remote.model

data class BellSchedule(
    val group: String? = null,
    val lessonDurationMin: Int = 0,
    val lessonsTime: List<String>
)