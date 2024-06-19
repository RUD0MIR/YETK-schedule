package com.yetk.for_student

sealed class WeekState(val textId: Int, val id: Int) {
    data object UpperWeek: WeekState(R.string.upper_week, 1)
    data object EveryWeek: WeekState(R.string.every_week, 0)
    data object LowerWeek: WeekState(R.string.lower_week, -1)

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





