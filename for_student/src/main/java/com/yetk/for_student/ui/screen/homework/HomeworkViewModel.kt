package com.yetk.for_student.ui.screen.homework

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yetk.for_student.domain.model.Homework
import com.yetk.for_student.domain.repository.HomeworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    private val repository: HomeworkRepository
) : ViewModel() {
    val homeworks = repository.getAll().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList()
    )

    fun checkCorrectInput(subjectTfValue: String, contentTfValue: String): Boolean {
        return subjectTfValue.isNotBlank() || contentTfValue.isNotBlank()
    }

    fun checkHomework(homeworkId: Int) {
        viewModelScope.launch {
            repository.deleteHomework(Homework(homeworkId))
        }
    }

    fun deleteHomework(homeworkId: Int) {
        viewModelScope.launch {
            repository.deleteHomework(Homework(homeworkId))
        }
    }

    fun insertHomework(homework: Homework) {
        viewModelScope.launch {
            repository.insertHomework(homework)
        }
    }

    fun updateHomework(homework: Homework) {
        viewModelScope.launch {
            repository.updateHomework(homework)
        }
    }

    companion object {
        private const val TAG = "HomeworkViewModel"
    }
}