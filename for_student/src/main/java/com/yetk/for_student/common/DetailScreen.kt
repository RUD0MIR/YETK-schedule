package com.yetk.for_student.common

sealed class DetailScreenType(val id: Int) {
    data object AddScreen : DetailScreenType(-1)
    class EditScreen(id: Int): DetailScreenType(id)
}