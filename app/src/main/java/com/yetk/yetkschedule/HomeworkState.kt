package com.yetk.yetkschedule

import com.yetk.yetkschedule.data.local.model.Homework

data class HomeworkState(
    val homeworks: List<Homework> = emptyList(),
    val content: String = "",
    val homeworkId: Int = -1,
    val subjectName: String = "",
    val isAddingHomework: Boolean = false,
    val isSubjectNameTextFieldError: Boolean = false,
    val isContentTextFieldError: Boolean = false
)

fun HomeworkState.cleanDetailScreenData(): HomeworkState {
    return HomeworkState(
        content = "",
        subjectName = "",
        homeworkId = -1,
        isSubjectNameTextFieldError = false,
        isContentTextFieldError = false
    )
}
