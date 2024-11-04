package com.yetk.forstudent.domain.repository

import com.yetk.forstudent.common.Response
import com.yetk.forstudent.domain.model.BellSchedule
import com.yetk.forstudent.domain.model.CollegeGroup
import kotlinx.coroutines.flow.Flow

interface CollegeGroupDataRepository {
    fun getCollegeGroupData(groupId: String): Flow<Response<CollegeGroup>>

    fun getBellSchedule(): Flow<Response<BellSchedule>>

    fun isLowerWeek(): Flow<Response<Boolean>>
}
