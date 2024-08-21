package com.yetk.for_student.domain.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Homework(
    val id: Int,
    val content: String = "",
    val subjectName: String = "",
    val isVisible: Boolean = true
)
