package com.yetk.yetkschedule.data.repository

import com.yetk.yetkschedule.data.local.model.Homework
import kotlinx.coroutines.flow.Flow

interface HomeworkRepository {
    suspend fun upsertHomework(homework: Homework)
    suspend fun updateHomework(homework: Homework)

    suspend fun deleteHomework(homework: Homework)

    fun getAll(): Flow<List<Homework>>

    suspend fun updateAll(homeworks: List<Homework>)

    fun getHomeworkById(id: Int): Flow<Homework>
}