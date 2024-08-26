package com.yetk.for_student.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yetk.for_student.data.model.HomeworkEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface HomeworkDao {

    @Insert
    suspend fun insertHomework(homework: HomeworkEntity)

    @Update
    suspend fun updateHomework(homework: HomeworkEntity)

    @Delete
    suspend fun deleteHomework(homework: HomeworkEntity)

    @Query("SELECT * FROM homeworkentity")
    fun getAll(): Flow<List<HomeworkEntity>>
}