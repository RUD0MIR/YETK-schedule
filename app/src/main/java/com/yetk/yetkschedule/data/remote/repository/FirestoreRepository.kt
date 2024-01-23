package com.yetk.yetkschedule.data.remote.repository

import com.yetk.yetkschedule.data.remote.model.BellSchedule

interface FirestoreRepository {
    fun getBellScheduleData(groupId: String): BellSchedule?
    fun getAuthedGroupId(login: String, password: String): String
}