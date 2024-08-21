package com.yetk.for_student.domain.model

import com.google.firebase.firestore.DocumentReference

data class BellSchedule(
    val lessonDurationMin: Int = 0,
    val lessonsTime: List<String> = emptyList()
)