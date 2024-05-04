package com.yetk.for_student.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.yetk.designsystem.icon.YetkIcon

enum class TopLevelDestination(
    val icon: ImageVector,
    val text: String,
) {
    SCHEDULE(
        icon = YetkIcon.Schedule,
        text = "Расписание",
    ),
    HOMEWORK(
        icon = YetkIcon.Homework,
        text = "Домашнее задание",
    ),
    BELLS(
        icon = YetkIcon.BellSchedule,
        text = "Расписание звонков",
    ),
    SUBJECTS(
        icon = YetkIcon.Subjects,
        text = "Предметы",
    ),
}