package com.yetk.for_student

sealed class Screen(val route: String) {
    object ScheduleScreen: Screen("schedule") {
        object LessonDetailScreen: Screen("${this.route}/lessonDetail")
    }
    object HomeworkScreen: Screen("homework") {
        object HomeworkDetailScreen: Screen("${this.route}/homeworkDetail")
    }
    object BellScreen: Screen("bell")
    object SubjectsScreen: Screen("subjects")
    object TeachersScreen: Screen("teachers")
    object TestScreen: Screen("test")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(this@Screen.route)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }

    fun passArgs(vararg args: String): String {
        return buildString {
            append(this@Screen.route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}