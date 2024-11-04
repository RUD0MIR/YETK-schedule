package com.yetk.forstudent.domain.model

data class CollegeGroup(
    val id: String,
    val login: String,
    val name: String,
    val password: String,
    val relevantFromTo: String,
    val lessons: List<Lesson>,
    val subjects: List<Subject>
)
