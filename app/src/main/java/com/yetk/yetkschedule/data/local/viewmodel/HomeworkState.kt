package com.yetk.yetkschedule.data.local.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.yetk.yetkschedule.data.local.model.Homework

data class HomeworkState(
    val homeworks: List<Homework> = emptyList(),
    val content: String = "",
    val homeworkId: Int = -1,
    val isHomeworkVisible: Boolean = true,
    val subjectName: TextFieldValue = TextFieldValue(),
    val isChecked: Boolean = false,
    val isDeleted: Boolean = false
)

fun HomeworkState.cleanDetailScreenData(): HomeworkState {
    return HomeworkState(
        content = "",
        subjectName = TextFieldValue(),
        homeworkId = -1,
        isHomeworkVisible = true,
        isChecked = false,
        isDeleted = false
    )
}
