package com.yetk.yetkschedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yetk.yetkschedule.data.local.model.Homework
import com.yetk.yetkschedule.data.repository.HomeworkRepository
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

                if (subjectName.isBlank()) {
                    _state.update {
                        it.copy(
                            isSubjectNameTextFieldError = true
                        )
                    }
                    return
                }

                val homework = Homework(
                    id = 0,
                    content = content,
                    subjectName = subjectName
                )

                viewModelScope.launch {
                    repository.upsertHomework(homework)
                }

                _state.update {
                    it.copy(
                        isAddingHomework = false,
                        content = content,
                        subjectName = subjectName,
                        isSubjectNameTextFieldError = false,
                        isContentTextFieldError = false,
                    )
                }
                _state.update { it.cleanDetailScreenData() }
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
            is HomeworkEvent.HomeworkChecked -> {
                viewModelScope.launch {
                    repository.deleteHomework(event.homework)
                }
                _state.update { it.cleanDetailScreenData() }
            }
            is HomeworkEvent.GetHomework -> {
                viewModelScope.launch {
                    repository.getHomeworkById(event.id)
                }
            }
            is HomeworkEvent.UpdateListIndexes -> {
                viewModelScope.launch {
                    repository.updateAll(event.homeworks)
                }
            }
        }
    }
}