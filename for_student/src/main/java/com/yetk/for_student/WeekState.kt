package com.yetk.for_student

enum class WeekState {
    UPPER_WEEK,
    LOWER_WEEK;

    operator fun not(): WeekState {
        return if(this == UPPER_WEEK) LOWER_WEEK else UPPER_WEEK
    }
}

enum class LessonWeekState {
    UPPER_WEEK,
    LOWER_WEEK,
    EVERY_WEEK
}



fun WeekState.name() : String {
    return when(this) {
        WeekState.UPPER_WEEK -> "Верхняя неделя"
        WeekState.LOWER_WEEK -> "Нижняя неделя"
    }
}

fun LessonWeekState.name() : String {
    return when(this) {
        LessonWeekState.UPPER_WEEK -> "Верхняя неделя"
        LessonWeekState.LOWER_WEEK -> "Нижняя неделя"
        LessonWeekState.EVERY_WEEK -> "Каждую неделю"
    }
}

