package com.yetk.yetkschedule.data.remote.repository

import com.yetk.yetkschedule.data.remote.model.BellSchedule
import com.yetk.yetkschedule.data.remote.model.CollegeGroup
import com.yetk.yetkschedule.data.remote.model.Response
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    fun getCollegeGroupData(groupId: String): Flow<Response<CollegeGroup>>
    fun getBellSchedule(): Flow<Response<BellSchedule>>
    fun isLowerWeek(): Flow<Response<Boolean>>
}