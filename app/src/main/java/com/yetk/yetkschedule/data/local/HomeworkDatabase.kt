package com.yetk.yetkschedule.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yetk.yetkschedule.data.local.model.Homework

@Database(
    entities = [Homework::class],
    version = 1
)
abstract class HomeworkDatabase: RoomDatabase() {

    abstract val dao: HomeworkDao
}