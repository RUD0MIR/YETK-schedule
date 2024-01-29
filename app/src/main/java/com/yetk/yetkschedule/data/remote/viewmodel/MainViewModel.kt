package com.yetk.yetkschedule.data.remote.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yetk.yetkschedule.data.remote.model.BellSchedule
import com.yetk.yetkschedule.data.remote.model.CollegeGroup
import com.yetk.yetkschedule.data.remote.model.Response
import com.yetk.yetkschedule.data.remote.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
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

    fun fetchBellScheduleData() = viewModelScope.launch {
        repository.getBellSchedule().collect { response ->
            bellSchedule.value = response
        }
    }
}