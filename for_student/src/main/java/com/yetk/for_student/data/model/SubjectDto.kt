package com.yetk.for_student.data.model

data class SubjectDto(
    val name: String = "",
    val teachers: List<String> = emptyList()
)