package com.yetk.for_student

import com.yetk.model.BellSchedule
import com.yetk.model.CollegeGroup
import com.yetk.model.Lesson
import com.yetk.model.Subject

object TestData {
    val collegeGroup = CollegeGroup(
        id = "1",
        name = "testName",
        relevantFromTo = "22.01.24 - 27.01.24",
        lessons =
            listOf(
                Lesson(
                    "Английский язык",
                    rooms = listOf("304"),
                    teachers = listOf("Колобова Р.В.", "Анна Н.Н."),
                    number = 1,
                    dayOfWeek = 0,
                    weekState = WeekState.UpperWeek.id
                ),
                Lesson(
                    "Математика",
                    rooms = listOf("208м"),
                    teachers = listOf("Иванова Б.В."),
                    number = 2,
                    dayOfWeek = 0,
                    weekState = WeekState.LowerWeek.id
                ),
                Lesson(
                    "Физкультура",
                    rooms = listOf("102"),
                    teachers = listOf("Георгиев Ж.В."),
                    number = 3,
                    dayOfWeek = 1,
                    weekState = WeekState.UpperWeek.id
                ),
                Lesson(
                    "Информатика",
                    rooms = listOf("103"),
                    teachers = listOf("Александра Г.Г."),
                    number = 4,
                    dayOfWeek = 1,
                    weekState = WeekState.UpperWeek.id
                ),
                Lesson(
                    "Физика",
                    rooms = listOf("404"),
                    teachers = listOf("Петров Г.Г."),
                    number = 5,
                    dayOfWeek = 2,
                    weekState = WeekState.EveryWeek.id
                )
            ),
        subjects = listOf(
            Subject("Английский язык", teachers = listOf("Колобова Р.В.", "Анна Н.Н.")),
            Subject("Математика", teachers = listOf("Иванова Б.В.")),
            Subject("Физкультура", teachers = listOf("Георгиев Ж.В.")),
            Subject("Информатика", teachers = listOf("Александра Г.Г.")),
            Subject("Физика", teachers = listOf("Петров Г.Г.")),
        )
    )

    val bellSchedule = BellSchedule(
        null,
        lesson_duration_min = 0,
        lessons_time = listOf(
            "08:00 - 09:30",
            "09:40 - 11:10",
            "11:20 - 12:50",
            "13:20 - 14:50",
            "15:00 - 16:30",
            "16:40 - 18:10",
        )
    )
}