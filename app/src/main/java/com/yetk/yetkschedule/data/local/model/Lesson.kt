package com.example.lessonsschedulemanagerv2.data.local.model

import com.yetk.yetkschedule.other.WeekState

data class Lesson(
    val id: Int,
    val subject: String,
    val room: String,
    val teacher: String,
    val startTime: String,//replace with LessonTime
    val endTime: String,//
    val dayOfWeek: Int,
    val weekState: WeekState,
    val hasHomework: Boolean,
    val isDisabled: Boolean
    )