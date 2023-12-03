package com.yetk.yetkschedule.other

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
            args.forEach { arg ->
                append("${this@Screen.route}/{$arg}")
            }
        }
    }

    fun passArgs(vararg args: String): String {
        return buildString {
            args.forEach { arg ->
                append("${this@Screen.route}/$arg")
            }
        }
    }
}