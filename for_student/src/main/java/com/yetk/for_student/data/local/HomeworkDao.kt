package com.yetk.for_student.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeworkDao {

    @Insert
    suspend fun insertHomework(homework: com.yetk.model.Homework)

    @Update
    suspend fun updateHomework(homework: com.yetk.model.Homework)

    @Delete
    suspend fun deleteHomework(homework: com.yetk.model.Homework)

    @Query("SELECT * FROM homework")
    fun getAll(): Flow<List<com.yetk.model.Homework>>

    @Update
    suspend fun updateAll(homeworks: List<com.yetk.model.Homework>)



    @Query("SELECT * FROM homework WHERE homework.id = :id")
    fun getHomeworkById(id: Int): Flow<com.yetk.model.Homework>
}