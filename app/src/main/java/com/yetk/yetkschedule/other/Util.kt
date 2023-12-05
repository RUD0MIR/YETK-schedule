package com.yetk.yetkschedule.other

import androidx.compose.ui.text.input.TextFieldValue
import kotlin.time.Duration

fun List<String>.filterDropdownMenu(value: TextFieldValue): List<String> {
    return this.filter {
        it.startsWith(value.text, ignoreCase = true) && it != value.text
    }.take(3)
}

fun Duration.parseNhNmin() : String {
    return this.toComponents { hours, minutes, _, _ ->
        var time = ""
        if(hours > 0L) time += "$hours h"
        if(minutes > 0L) time += "$minutes min"
        else time = "0"

        time
    }
}