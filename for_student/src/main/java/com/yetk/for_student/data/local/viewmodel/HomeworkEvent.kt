package com.yetk.for_student.data.local.viewmodel

import com.yetk.model.Homework

sealed interface HomeworkEvent {
    data object SaveHomework : HomeworkEvent
    data class UpdateId(val id: Int): HomeworkEvent
    data class UpdateSubjectName(val subjectName: String): HomeworkEvent
    data class UpdateHomeworkDetail(val homework: Homework?): HomeworkEvent
    data class UpdateContent(val content: String): HomeworkEvent
    data class DeleteHomework(val homework: com.yetk.model.Homework): HomeworkEvent
    data class HomeworkChecked(val homework: com.yetk.model.Homework): HomeworkEvent
    data class ShowHomework(val homework: com.yetk.model.Homework): HomeworkEvent
    data class HideHomework(val homework: com.yetk.model.Homework): HomeworkEvent
    data class GetHomework(val id: Int): HomeworkEvent
    data object ClearState : HomeworkEvent
}