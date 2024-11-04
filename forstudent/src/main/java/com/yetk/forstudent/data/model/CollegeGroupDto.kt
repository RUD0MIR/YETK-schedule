package com.yetk.forstudent.data.model

data class CollegeGroupDto(
    val id: String = "",
    val login: String = "",
    val name: String = "",
    val password: String = "",
    val relevantFromTo: String = "",
    val lessons: List<LessonDto> = emptyList(),
    val subjects: List<SubjectDto> = emptyList()
)
