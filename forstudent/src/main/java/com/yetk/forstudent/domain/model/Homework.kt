package com.yetk.forstudent.domain.model

data class Homework(
    val id: Int,
    val content: String = "",
    val subjectName: String = "",
    val isVisible: Boolean = true
)
