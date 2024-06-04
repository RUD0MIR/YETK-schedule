package com.yetk.for_student.data.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yetk.for_student.data.local.repository.HomeworkRepository
import com.yetk.model.Homework
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    private val repository: HomeworkRepository
) : ViewModel() {
    private val _homeworks = repository.getAll()

    private val _state = MutableStateFlow(HomeworkState())
    val state = combine(_state, _homeworks) { state, homeworks ->
        state.copy(
            homeworks = homeworks
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeworkState())

    fun onEvent(event: HomeworkEvent) {
        when (event) {
            is HomeworkEvent.DeleteHomework -> {
                viewModelScope.launch {
                    repository.deleteHomework(event.homework)
                }
                _state.update { it.cleanDetailScreenData() }
            }
            HomeworkEvent.SaveHomework -> {
                val content = state.value.content
                val subjectName = state.value.subjectName
                val homeworkId = state.value.homeworkId

                if (subjectName.isBlank() && content.isBlank()) {
                    return
                }

                val homework = Homework(
                    id = if (homeworkId != -1) homeworkId else 0,
                    content = content,
                    subjectName = subjectName
                )

                viewModelScope.launch {
                    repository.upsertHomework(homework)
                }
            }
            is HomeworkEvent.UpdateContent -> {
                _state.update {
                    it.copy(
                        content = event.content,
                    )
                }
            }
            is HomeworkEvent.UpdateSubjectName -> {
                _state.update {
                    it.copy(
                        subjectName = event.subjectName
                    )
                }
            }
            is HomeworkEvent.UpdateId -> {
                _state.update {
                    it.copy(
                        homeworkId = event.id
                    )
                }
            }
            is HomeworkEvent.HomeworkChecked -> {
                viewModelScope.launch {
                    repository.deleteHomework(event.homework)
                }

            }
            is HomeworkEvent.GetHomework -> {
                viewModelScope.launch {
                    repository.getHomeworkById(event.id)
                }
            }
            is HomeworkEvent.ShowHomework -> {
                val homework = Homework(
                    id = event.homework.id,
                    content = event.homework.content,
                    subjectName = event.homework.subjectName,
                    isVisible = true
                )
                viewModelScope.launch {
                    repository.updateHomework(homework)
                }
            }
            is HomeworkEvent.HideHomework -> {
                val homework = Homework(
                    id = event.homework.id,
                    content = event.homework.content,
                    subjectName = event.homework.subjectName,
                    isVisible = false
                )
                viewModelScope.launch {
                    repository.updateHomework(homework)
                }
            }
            HomeworkEvent.ClearState -> {
                _state.update { it.cleanDetailScreenData() }
            }

            is HomeworkEvent.UpdateHomeworkDetail -> {
                _state.update {
                    it.copy(
                        homeworkDetail = event.homework
                    )
                }
            }
        }
    }
}