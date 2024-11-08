package com.yetk.forstudent.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yetk.forstudent.common.Response
import com.yetk.forstudent.domain.model.BellSchedule
import com.yetk.forstudent.domain.model.CollegeGroup
import com.yetk.forstudent.domain.repository.CollegeGroupDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class StudentViewModel
@Inject
constructor(
    private val repository: CollegeGroupDataRepository
//    private val groupId: String
) : ViewModel() {
    private var _bellSchedule = MutableStateFlow<Response<BellSchedule>>(Response.Loading)
    val bellSchedule = _bellSchedule.asStateFlow()

    private var _collegeGroup = MutableStateFlow<Response<CollegeGroup>>(Response.Loading)
    val collegeGroup = _collegeGroup.asStateFlow()

    private var _isLowerWeek = MutableStateFlow<Response<Boolean>>(Response.Loading)
    val isLowerWeek = _isLowerWeek.asStateFlow()

    init {
        fetchCollegeGroupData("6wBXWrOgataTaazTBhBn")
        fetchIsLowerWeek()
        fetchBellScheduleData()
    }

    fun getSubjectsNames(): List<String> {
        return if (collegeGroup.value is Response.Success) {
            (collegeGroup.value as Response.Success<CollegeGroup>).data.subjects.map {
                it.name
            }
        } else {
            emptyList()
        }
    }

    private fun fetchCollegeGroupData(groupId: String) = viewModelScope.launch {
        repository.getCollegeGroupData(groupId).collect { response ->
            _collegeGroup.value = response
        }
    }

    private fun fetchIsLowerWeek() = viewModelScope.launch {
        repository.isLowerWeek().collect { response ->
            _isLowerWeek.value = response
        }
    }

    private fun fetchBellScheduleData() = viewModelScope.launch {
        repository.getBellSchedule().collect { response ->
            _bellSchedule.value = response
        }
    }

    companion object {
        private const val TAG = "StudentViewModel"
    }
}
