package com.yetk.for_student.data.remote

import com.yetk.model.CollegeGroup
import com.yetk.model.Lesson
import com.yetk.model.Subject


class TestData {
    val collegeGroup1 = CollegeGroup(
        id = "6wBXWrOgataTaazTBhBn",
        login = "20kis-1",
        name = "20 КИС-1",
        password = "1234",
        relevantFromTo = "22.01.24 - 27.01.24",
        lessons = listOf(
            Lesson(
                subject = "Английский язык",
                rooms = listOf("304", "108"),
                teachers = listOf("Колобова Р.В.", "Фомина Р.И."),
                number = 1,
                dayOfWeek = 0,
                weekState = 0,
                isCanceled = false,
            ),
            Lesson(
                subject = "Физкультура",
                rooms = listOf("300"),
                teachers = listOf("Иванов И.И."),
                number = 2,
                dayOfWeek = 0,
                weekState = 1,
                isCanceled = false,
            ),
            Lesson(
                subject = "Информационные технологии",
                rooms = listOf("207"),
                teachers =  listOf("Никулина Р.И."),
                number = 3,
                dayOfWeek = 1,
                weekState = -1,
                isCanceled = false,
            )
        ),
            subjects = listOf(
                Subject(
                    name = "Английский язык",
                    teachers = listOf("Колобова Р.В.", "Фомина Р.И.")
                ),
                Subject(
                    name = "Физкультура",
                    teachers = listOf("Иванов И.И.")
                ),
                Subject(
                    name = "Информационные технологии",
                    teachers = listOf("Никулина Р.И.")
                )
            )
        )

    val collegeGroup2 = CollegeGroup(
        id = "??",
        login = "20kis-2",
        name = "20 КИС-2",
        password = "1234",
        relevantFromTo = "22.01.24 - 27.01.24",
        lessons = listOf(
            Lesson(
                subject = "Русский язык",
                rooms = listOf("304", "108"),
                teachers = listOf("Колобова Р.В.", "Фомина Р.И."),
                number = 1,
                dayOfWeek = 3,
                weekState = 0,
                isCanceled = false,
            ),
            Lesson(
                subject = "Физкультура",
                rooms = listOf("300"),
                teachers = listOf("Иванов И.И."),
                number = 2,
                dayOfWeek = 3,
                weekState = -1,
                isCanceled = false,
            )
        ),
        subjects = listOf(
            Subject(
                name = "Русский язык",
                teachers = listOf("Колобова Р.В.", "Фомина Р.И.")
            ),
            Subject(
                name = "Физкультура",
                teachers = listOf("Иванов И.И.")
            )
        )
    )
}
