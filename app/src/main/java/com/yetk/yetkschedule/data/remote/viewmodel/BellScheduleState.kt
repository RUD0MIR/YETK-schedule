package com.yetk.yetkschedule.data.remote.viewmodel

data class BellScheduleState(
    val lessonDurationMin: Int,
    val lessonsTime: List<String>
)


fun BellScheduleState.cleanDetailScreenData(): BellScheduleState {
    return BellScheduleState(
        lessonDurationMin = 0,
        lessonsTime = emptyList()
    )
}
