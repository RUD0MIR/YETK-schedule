package com.yetk.for_student.domain.repository

import com.yetk.for_student.domain.model.Homework
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface HomeworkRepository {
    suspend fun insertHomework(homework: Homework)
    suspend fun updateHomework(homework: Homework)

    suspend fun deleteHomework(homework: Homework)

    fun getAll(): Flow<List<Homework>>

    fun getHomeworkById(id: Int): Flow<Homework>
}