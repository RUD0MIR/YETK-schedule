package com.yetk.yetkschedule.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.yetk.yetkschedule.data.local.HomeworkDao
import com.yetk.yetkschedule.data.local.HomeworkDatabase
import com.yetk.yetkschedule.data.local.repository.DefaultHomeworkRepository
import com.yetk.yetkschedule.data.local.repository.HomeworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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
    fun provideNoteDao(db: HomeworkDatabase) = db.dao

    @Provides
    @Singleton
    fun provideFirestore() = Firebase.firestore
}