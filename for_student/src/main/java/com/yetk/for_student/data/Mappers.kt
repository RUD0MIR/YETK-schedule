package com.yetk.for_student.data

import com.yetk.for_student.data.model.BellScheduleDto
import com.yetk.for_student.data.model.CollegeGroupDto
import com.yetk.for_student.data.model.HomeworkEntity
import com.yetk.for_student.data.model.LessonDto
import com.yetk.for_student.data.model.SubjectDto
import com.yetk.for_student.domain.model.BellSchedule
import com.yetk.for_student.domain.model.CollegeGroup
import com.yetk.for_student.domain.model.Homework
import com.yetk.for_student.domain.model.Lesson
import com.yetk.for_student.domain.model.Subject

fun BellScheduleDto.toDomain() =
    BellSchedule(
        lessonDurationMin = lesson_duration_min,
        lessonsTime = lessons_time
    )

fun CollegeGroupDto.toDomain() =
    CollegeGroup(
        id = id,
        login = login,
        name = name,
        password = password,
        relevantFromTo = relevantFromTo,
        lessons = lessons.map { it.toDomain() },
        subjects = subjects.map { it.toDomain() }

    )

fun LessonDto.toDomain() =
    Lesson(
        subject = subject,
        rooms = rooms,
        teachers = teachers,
        number = number,
        dayOfWeek = dayOfWeek,
        weekState = weekState,
        isCanceled = isCanceled
    )

fun SubjectDto.toDomain() =
    Subject(
        name = name,
        teachers = teachers
    )

fun HomeworkEntity.toDomain() =
    Homework(
        id = id,
        content = content ?: "",
        subjectName = subjectName ?: "",
        isVisible = isVisible
    )

fun Homework.toData() =
    HomeworkEntity(
        id = id,
        content = content,
        subjectName = subjectName,
        isVisible = isVisible
    )