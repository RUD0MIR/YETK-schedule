package com.yetk.yetkschedule.data.remote.model

data class CollegeGroup(
    val id: String = "",
    val login: String = "",
    val name: String = "",
    val password: String = "",
    val relevantFromTo: String = "",
    val lessons: List<Lesson> = emptyList(),
    val subjects: List<Subject> = emptyList()
)