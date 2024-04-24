package com.yetk.for_student.data.local.repository

import kotlinx.coroutines.flow.Flow

interface HomeworkRepository {
    suspend fun upsertHomework(homework: com.yetk.model.Homework)
    suspend fun updateHomework(homework: com.yetk.model.Homework)

    suspend fun deleteHomework(homework: com.yetk.model.Homework)

    fun getAll(): Flow<List<com.yetk.model.Homework>>

    suspend fun updateAll(homeworks: List<com.yetk.model.Homework>)

    fun getHomeworkById(id: Int): Flow<com.yetk.model.Homework>
}