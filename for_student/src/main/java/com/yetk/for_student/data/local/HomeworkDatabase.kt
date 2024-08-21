package com.yetk.for_student.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yetk.for_student.data.model.HomeworkEntity

@Database(
    entities = [HomeworkEntity::class],
    version = 1
)
abstract class HomeworkDatabase: RoomDatabase() {

    abstract val dao: HomeworkDao
}