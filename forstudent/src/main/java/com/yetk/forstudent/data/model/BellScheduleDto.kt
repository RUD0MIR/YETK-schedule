package com.yetk.forstudent.data.model

import com.google.firebase.firestore.DocumentReference

data class BellScheduleDto(
    val group: DocumentReference? = null,
    val lesson_duration_min: Int = 0,
    val lessons_time: List<String> = emptyList()
)
