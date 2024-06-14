package com.yetk.for_student.data.remote.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yetk.for_student.data.remote.repository.FirestoreRepository
import com.yetk.model.BellSchedule
import com.yetk.model.CollegeGroup
import com.yetk.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "StudentViewModel"

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: FirestoreRepository,
//    private val groupId: String
) : ViewModel() {
    var bellSchedule = mutableStateOf<Response<BellSchedule>>(Response.Loading)
        private set
    var collegeGroup = mutableStateOf<Response<CollegeGroup>>(Response.Loading)
        private set
    var isLowerWeek = mutableStateOf<Response<Boolean>>(Response.Loading)
        private set

    init {
        fetchCollegeGroupData("6wBXWrOgataTaazTBhBn")
        fetchIsLowerWeek()
        fetchBellScheduleData()
    }

    fun getSubjectsNames(): List<String> {
        val subjectsNames = mutableListOf<String>()
        if (collegeGroup.value is Response.Success) {
            val collegeGroupData = collegeGroup as MutableState<Response.Success<CollegeGroup>>
            collegeGroupData.value.data.subjects.map {
                subjectsNames.add(it.name)
            }
        }
        Log.d(TAG, "subjectsNames.size:${subjectsNames.size} ")
        return subjectsNames
    }

    private fun fetchCollegeGroupData(groupId: String) = viewModelScope.launch {
        repository.getCollegeGroupData(groupId).collect { response ->
            collegeGroup.value = response
        }
    }

    private fun fetchIsLowerWeek() = viewModelScope.launch {
        repository.isLowerWeek().collect { response ->
            isLowerWeek.value = response
        }
    }

    private fun fetchBellScheduleData() = viewModelScope.launch {
        repository.getBellSchedule().collect { response ->
            bellSchedule.value = response
        }
    }
}