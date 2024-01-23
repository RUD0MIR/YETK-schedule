package com.yetk.yetkschedule.data.remote.viewmodel

sealed interface BellScheduleEvent {

    data class BellSchedule(val groupId: Int): BellScheduleEvent
}