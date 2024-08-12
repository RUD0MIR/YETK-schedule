package com.yetk.for_student.data.local

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yetk.for_student.data.local.repository.HomeworkRepository
import com.yetk.model.Homework
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeworkViewModel"

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
            repository.deleteHomework(Homework(homeworkId, null, null))
        }
    }

    fun deleteHomework(homeworkId: Int) {
        viewModelScope.launch {
            repository.deleteHomework(Homework(homeworkId, null, null))
        }
    }

    fun insertHomework(homework: Homework) {
        Log.d(TAG, "insertHomework called")
        viewModelScope.launch {
            repository.insertHomework(homework)
            Log.d(TAG, "homework inserted: $homework")
        }
    }

    fun updateHomework(homework: Homework) {
        Log.d(TAG, "update called")
        viewModelScope.launch {
            repository.updateHomework(homework)
        }
    }
}