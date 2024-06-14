package com.yetk.for_student.data.local.viewmodel

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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    private val repository: HomeworkRepository
) : ViewModel() {
    val homeworks = repository.getAll()
//    private val _homeworks = MutableStateFlow<List<ExploreModel>>(emptyList())

    fun checkHomework(homeworkId: Int) {
        viewModelScope.launch {
            //checkbox animation delay
            delay(1000)
            repository.deleteHomework(Homework(homeworkId, null, null))
        }
    }

    fun deleteHomework(homeworkId: Int) {
        viewModelScope.launch {
            repository.deleteHomework(Homework(homeworkId, null, null))
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
}