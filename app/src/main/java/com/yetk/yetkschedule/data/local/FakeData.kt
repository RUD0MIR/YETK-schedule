package com.yetk.yetkschedule.data.local

import com.yetk.yetkschedule.data.local.model.Lesson
import com.yetk.yetkschedule.other.Constants
import com.yetk.yetkschedule.other.LessonWeekState

class FakeData {
    companion object {
        val monItems = mutableListOf(
            Lesson(
                id = 2,
                subject = "Экономика отрасли",
                room = "302м",
                teacher = "Петров И.И.",
                startTime = "11:20",
                endTime = "12:50",
                weekState = LessonWeekState.UPPER_WEEK,
                dayOfWeek = Constants.MON,
                isDisabled = false
            ),

            Lesson(
                id = 3,
                subject = "Физкультура",
                room = "302",
                teacher = "Иванов Е.И.",
                startTime = "13:20",
                endTime = "14:50",
                weekState = LessonWeekState.EVERY_WEEK,
                dayOfWeek = Constants.MON,
                isDisabled = false
            ),
            Lesson(
                id = 0,
                subject = "Информационные технологии",
                room = "401м",
                teacher = "Андреева А.А.",
                startTime = "15:00",
                endTime = "16:30",
                weekState = LessonWeekState.EVERY_WEEK,
                dayOfWeek = Constants.MON,
                isDisabled = false
            ),
            Lesson(
                id = 1,
                subject = "Внедрение и поддержка КС",
                room = "201",
                teacher = "Петров И.И.",
                startTime = "16:40",
                endTime = "18:10",
                weekState = LessonWeekState.EVERY_WEEK,
                dayOfWeek = Constants.MON,
                isDisabled = false
            ),
        )
        val tueItems = mutableListOf(
            Lesson(
                id = 3,
                subject = "Физкультура",
                room = "302",
                teacher = "Иванов Е.И.",
                startTime = "13:20",
                endTime = "14:50",
                weekState = LessonWeekState.EVERY_WEEK,
                dayOfWeek = Constants.MON,
                isDisabled = false
            ),
            Lesson(
                id = 3,
                subject = "Физкультура",
                room = "302",
                teacher = "Иванов Е.И.",
                startTime = "15:00",
                endTime = "16:30",
                weekState = LessonWeekState.EVERY_WEEK,
                dayOfWeek = Constants.MON,
                isDisabled = false
            ),
        )
        val wedItems = mutableListOf<Lesson>()
        val thuItems = mutableListOf<Lesson>()
        val friItems = mutableListOf<Lesson>()
        val satItems = mutableListOf<Lesson>()
        val lessonListItems = mutableListOf(
            monItems,
            tueItems,
            wedItems,
            thuItems,
            friItems,
            satItems
        )
    }
}