package com.yetk.forstudent.domain.repository

import com.yetk.forstudent.domain.model.Homework
import kotlinx.coroutines.flow.Flow

interface HomeworkRepository {
    suspend fun insertHomework(homework: Homework)

    suspend fun updateHomework(homework: Homework)

    suspend fun deleteHomework(homework: Homework)

    fun getAll(): Flow<List<Homework>>
}
