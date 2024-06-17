package com.yetk.for_student

sealed class WeekState(val text: String, val id: Int) {
    object UpperWeek: WeekState("Верхняя неделя", 1)
    object EveryWeek: WeekState("Каждую неделю", 0)
    object LowerWeek: WeekState("Нижняя неделя", -1)

    operator fun not(): WeekState {
        return if(this == UpperWeek) LowerWeek else UpperWeek
    }
}
//-1 isLowerWeek=true
fun Int.matchesWeekState(isLowerWeek: Boolean): Boolean {
    return when(this) {
         WeekState.UpperWeek.id -> {
            !isLowerWeek
        }
        WeekState.LowerWeek.id -> {
            isLowerWeek
        }
        else -> {
            true
        }
    }
}





