package com.yetk.forstudent.data

import com.yetk.forstudent.data.model.BellScheduleDto
import com.yetk.forstudent.data.model.CollegeGroupDto
import com.yetk.forstudent.data.model.HomeworkEntity
import com.yetk.forstudent.data.model.LessonDto
import com.yetk.forstudent.data.model.SubjectDto
import com.yetk.forstudent.domain.model.BellSchedule
import com.yetk.forstudent.domain.model.CollegeGroup
import com.yetk.forstudent.domain.model.Homework
import com.yetk.forstudent.domain.model.Lesson
import com.yetk.forstudent.domain.model.Subject

fun BellScheduleDto.toDomain() = BellSchedule(
    lessonDurationMin = lesson_duration_min,
    lessonsTime = lessons_time
)

fun CollegeGroupDto.toDomain() = CollegeGroup(
    id = id,
    login = login,
    name = name,
    password = password,
    relevantFromTo = relevantFromTo,
    lessons = lessons.map { it.toDomain() },
    subjects = subjects.map { it.toDomain() }
)

fun LessonDto.toDomain() = Lesson(
    subject = subject,
    rooms = rooms,
    teachers = teachers,
    number = number,
    dayOfWeek = dayOfWeek,
    weekState = weekState,
    isCanceled = isCanceled
)

fun SubjectDto.toDomain() = Subject(
    name = name,
    teachers = teachers
)

fun HomeworkEntity.toDomain() = Homework(
    id = id,
    content = content ?: "",
    subjectName = subjectName ?: "",
    isVisible = isVisible
)

fun Homework.toData() = HomeworkEntity(
    id = id,
    content = content,
    subjectName = subjectName,
    isVisible = isVisible
)
