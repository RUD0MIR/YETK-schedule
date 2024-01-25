package com.yetk.yetkschedule.data.remote.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yetk.yetkschedule.data.remote.model.BellSchedule
import com.yetk.yetkschedule.data.remote.model.Response
import com.yetk.yetkschedule.data.remote.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BellScheduleViewModel @Inject constructor(
    private val repository: FirestoreRepository,
//    private val groupId: String
) : ViewModel() {
    var bellScheduleResponse = mutableStateOf<Response<BellSchedule>>(Response.Loading)
    private set

    fun fetchBellScheduleData() = viewModelScope.launch {
        repository.getBellScheduleData("fdfdf").collect { response ->
            bellScheduleResponse.value = response
        }
    }
}