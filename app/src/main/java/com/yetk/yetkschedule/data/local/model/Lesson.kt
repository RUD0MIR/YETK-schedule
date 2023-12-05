package com.yetk.yetkschedule.data.local.model

import com.yetk.yetkschedule.other.LessonWeekState


data class Lesson(
    val id: Int,
    val subject: String,
    val room: String,
    val teacher: String,
    val startTime: String,//replace with LessonTime
    val endTime: String,//
    val dayOfWeek: Int,
    val weekState: LessonWeekState,
    val isDisabled: Boolean
    )