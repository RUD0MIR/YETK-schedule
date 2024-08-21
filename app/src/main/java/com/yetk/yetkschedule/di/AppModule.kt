package com.yetk.yetkschedule.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.yetk.for_student.data.remote.repository.CollegeGroupDataRepoImpl
import com.yetk.for_student.domain.repository.CollegeGroupDataRepository
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
    fun provideFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideDefaultFirestoreRepository(
        firestore: FirebaseFirestore
    ) = CollegeGroupDataRepoImpl(firestore) as CollegeGroupDataRepository
}