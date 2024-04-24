package com.yetk.model

data class CollegeGroup(
    val id: String = "",
    val login: String = "",
    val name: String = "-- ---_-",
    val password: String = "",
    val relevantFromTo: String = "--.--.--_--.--.--",
    val lessons: List<Lesson> = emptyList(),
    val subjects: List<Subject> = emptyList()
)