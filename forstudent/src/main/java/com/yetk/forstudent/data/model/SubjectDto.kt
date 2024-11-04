package com.yetk.forstudent.data.model

data class SubjectDto(
    val name: String = "",
    val teachers: List<String> = emptyList()
)
