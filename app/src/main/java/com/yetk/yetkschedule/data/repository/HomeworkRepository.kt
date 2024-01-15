package com.yetk.yetkschedule.data.repository

import androidx.room.Delete
import androidx.room.Upsert
import com.yetk.yetkschedule.data.local.model.Homework
import kotlinx.coroutines.flow.Flow

interface HomeworkRepository {

    @Upsert
    suspend fun upsertHomework(homework: Homework)

    @Delete
    suspend fun deleteHomework(homework: Homework)

    fun getAll(): Flow<List<Homework>>

    fun getHomeworkById(id: Int): Flow<Homework>
}