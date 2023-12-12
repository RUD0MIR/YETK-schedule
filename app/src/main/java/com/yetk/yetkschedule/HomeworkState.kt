package com.yetk.yetkschedule

import com.yetk.yetkschedule.data.local.model.Homework

data class HomeworkState(
    val homeworks: List<Homework> = emptyList(),
    val content: String = "",
    val subjectName: String = "",
    val isAddingHomework: Boolean = false,
    val isSubjectNameTextFieldError: Boolean = false,
    val isContentTextFieldError: Boolean = false
)

fun HomeworkState.cleanDetailScreenData(): HomeworkState {
    return HomeworkState(
        content = "",
        subjectName = "",
        isSubjectNameTextFieldError = false,
        isContentTextFieldError = false
    )
}
