package com.yetk.forstudent.common

import android.content.Context
import com.yetk.for_student.R
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Int.parseNhNmin(context: Context): String {
    return this.toDuration(DurationUnit.MINUTES).toComponents { hours, minutes, _, _ ->
        var time = ""
        if (hours > 0L) time += context.getString(R.string.hour_format, hours.toString())
        if (minutes > 0L) {
            time += context.getString(R.string.minutes_format, minutes.toString())
        } else {
            time = "0 "
        }

        time
    }
}
