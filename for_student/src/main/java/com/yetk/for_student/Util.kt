package com.yetk.for_student

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun List<String>.filterDropdownMenu(value: TextFieldValue): List<String> {
    return this.filter {
        it.startsWith(value.text, ignoreCase = true) && it != value.text
    }.take(3)
}

fun print(tag: String, e: Exception?) = e?.apply {
    Log.e(tag, stackTraceToString())
}

fun Int.matches(isLowerWeek: Boolean): Boolean {
    return when(this) {
        1 -> {
            !isLowerWeek
        }
        -1 -> {
            isLowerWeek
        }
        else -> {
            true
        }
    }
}

fun Int.parseNhNmin() : String {
    return this.toDuration(DurationUnit.MINUTES).toComponents { hours, minutes, _, _ ->
        var time = ""
        if(hours > 0L) time += "$hours ч "
        if(minutes > 0L) time += "$minutes мин "
        else time = "0 "

        time
    }
}