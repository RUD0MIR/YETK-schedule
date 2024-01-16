package com.yetk.yetkschedule.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Homework(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val content: String?,
    @ColumnInfo(name = "subject_name")
    val subjectName: String?,
)
