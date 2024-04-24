package com.yetk.for_student.data.remote.repository

import com.yetk.model.BellSchedule
import com.yetk.model.CollegeGroup
import com.yetk.model.Response
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    fun getCollegeGroupData(groupId: String): Flow<Response<CollegeGroup>>
    fun getBellSchedule(): Flow<Response<BellSchedule>>
    fun isLowerWeek(): Flow<Response<Boolean>>
}