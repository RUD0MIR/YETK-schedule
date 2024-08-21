package com.yetk.for_student.di

import android.app.Application
import androidx.room.Room
import com.yetk.for_student.data.local.HomeworkDao
import com.yetk.for_student.data.local.HomeworkDatabase
import com.yetk.for_student.data.local.DefaultHomeworkRepository
import com.yetk.for_student.domain.repository.HomeworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeworkModule {
    @Provides
    @Singleton
    fun provideHomeworkDatabase(app: Application): HomeworkDatabase {
        return Room.databaseBuilder(
            app,
            HomeworkDatabase::class.java,
            "note.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDefaultHomeworkRepository(
        dao: HomeworkDao
    ) = DefaultHomeworkRepository(dao) as HomeworkRepository

    @Provides
    @Singleton
    fun provideHomeworkDao(db: HomeworkDatabase) = db.dao
}