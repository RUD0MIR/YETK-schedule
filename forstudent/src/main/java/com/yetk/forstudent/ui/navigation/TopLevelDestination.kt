package com.yetk.forstudent.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.yetk.designsystem.icon.YetkIcon
import com.yetk.for_student.R

enum class TopLevelDestination(
    val icon: ImageVector,
    val textId: Int
) {
    SCHEDULE(
        icon = YetkIcon.Schedule,
        textId = R.string.schedule
    ),
    HOMEWORK(
        icon = YetkIcon.Homework,
        textId = R.string.homework
    ),
    BELLS(
        icon = YetkIcon.BellSchedule,
        textId = R.string.bell_schedule
    ),
    SUBJECTS(
        icon = YetkIcon.Subjects,
        textId = R.string.subjects
    )
}
