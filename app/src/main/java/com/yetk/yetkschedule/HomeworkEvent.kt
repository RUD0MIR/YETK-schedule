package com.yetk.yetkschedule

import androidx.compose.ui.text.input.TextFieldValue
import com.yetk.yetkschedule.data.local.model.Homework

sealed interface HomeworkEvent {
    data object SaveHomework : HomeworkEvent
    data class UpdateId(val id: Int): HomeworkEvent
    data class UpdateSubjectName(val subjectName: TextFieldValue): HomeworkEvent
    data class UpdateContent(val content: String): HomeworkEvent
    data class DeleteHomework(val homework: Homework): HomeworkEvent
    data class HomeworkChecked(val homework: Homework): HomeworkEvent
    data class ShowHomework(val homework: Homework): HomeworkEvent
    data class HideHomework(val homework: Homework): HomeworkEvent
    data class GetHomework(val id: Int): HomeworkEvent
    data object ClearState : HomeworkEvent
}