package com.yetk.yetkschedule.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Homework(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val content: String?,
    val subjectName: String?,
)
