package com.yetk.yetkschedule.data.remote.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yetk.yetkschedule.data.remote.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class BellScheduleViewModel @Inject constructor(
    private val repository: FirestoreRepository,
//    private val groupId: String
) : ViewModel() {
    private val _bellScheduleData = repository.getBellScheduleData("OuwcuqxGYXYw5XoOESLs")

    private val _state = mutableStateOf(
        BellScheduleState(
            lessonDurationMin = _bellScheduleData?.lessonDurationMin ?: 0,
            lessonsTime = _bellScheduleData?.lessonsTime ?: emptyList()
        )
    )
    val state = _state.value
}