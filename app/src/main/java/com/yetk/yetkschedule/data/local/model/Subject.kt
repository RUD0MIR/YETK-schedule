package com.yetk.yetkschedule.data.local.model

data class Subject(
    val id: Int,
    val name: String,
    val teachers: List<Teacher>
)