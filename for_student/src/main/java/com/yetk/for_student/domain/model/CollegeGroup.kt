package com.yetk.for_student.domain.model

import com.yetk.for_student.data.model.LessonDto
import com.yetk.for_student.data.model.SubjectDto

data class CollegeGroup(
    val id: String,
    val login: String,
    val name: String,
    val password: String,
    val relevantFromTo: String,
    val lessons: List<Lesson>,
    val subjects: List<Subject>
)