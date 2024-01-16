package com.yetk.yetkschedule

import com.yetk.yetkschedule.data.local.model.Homework

sealed interface HomeworkEvent {
    data object SaveHomework : HomeworkEvent
    data class UpdateListIndexes(val homeworks: List<Homework>): HomeworkEvent
    data class UpdateSubjectName(val subjectName: String): HomeworkEvent
    data class UpdateContent(val content: String): HomeworkEvent
    data class DeleteHomework(val homework: Homework): HomeworkEvent
    data class HomeworkChecked(val homework: Homework): HomeworkEvent
    data class GetHomework(val id: Int): HomeworkEvent
}