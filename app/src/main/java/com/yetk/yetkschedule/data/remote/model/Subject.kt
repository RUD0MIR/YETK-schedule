package com.yetk.yetkschedule.data.remote.model

data class Subject(
    val id: Int,
    val name: String,
    val teachers: List<Teacher>
)