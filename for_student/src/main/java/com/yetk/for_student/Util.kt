package com.yetk.for_student

import android.content.Context
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun print(tag: String, e: Exception?) = e?.apply {
    Log.e(tag, stackTraceToString())
}

fun Int.parseNhNmin(context: Context) : String {
    return this.toDuration(DurationUnit.MINUTES).toComponents { hours, minutes, _, _ ->
        var time = ""
        if(hours > 0L) time += context.getString(R.string.hour_format, hours.toString())
        if(minutes > 0L) time += context.getString(R.string.minutes_format, minutes.toString())
        else time = "0 "

        time
    }
}