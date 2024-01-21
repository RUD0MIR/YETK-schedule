package com.yetk.yetkschedule.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.yetk.yetkschedule.data.local.model.Homework
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeworkDao {

    @Upsert
    suspend fun upsertHomework(homework: Homework)

    @Update
    suspend fun updateHomework(homework: Homework)

    @Delete
    suspend fun deleteHomework(homework: Homework)

    @Query("SELECT * FROM homework")
    fun getAll(): Flow<List<Homework>>

    @Update
    suspend fun updateAll(homeworks: List<Homework>)



    @Query("SELECT * FROM homework WHERE homework.id = :id")
    fun getHomeworkById(id: Int): Flow<Homework>
}