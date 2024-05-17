package com.yetk.for_student.data.local.viewmodel

import com.yetk.model.Homework

data class HomeworkState(
    val homeworks: List<Homework> = emptyList(),
    val content: String = "",
    val homeworkId: Int = -1,
    val isHomeworkVisible: Boolean = true,
    val subjectName: String = "",
    val isChecked: Boolean = false,
    val isDeleted: Boolean = false
)

fun HomeworkState.cleanDetailScreenData(): HomeworkState {
    return HomeworkState(
        content = "",
        subjectName = "",
        homeworkId = -1,
        isHomeworkVisible = true,
        isChecked = false,
        isDeleted = false
    )
}
