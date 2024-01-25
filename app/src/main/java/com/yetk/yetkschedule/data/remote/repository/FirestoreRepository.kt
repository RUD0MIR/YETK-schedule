package com.yetk.yetkschedule.data.remote.repository

import com.yetk.yetkschedule.data.remote.model.BellSchedule
import com.yetk.yetkschedule.data.remote.model.Response
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    fun getBellScheduleData(groupId: String): Flow<Response<BellSchedule>>
    fun getAuthedGroupId(login: String, password: String): String
}