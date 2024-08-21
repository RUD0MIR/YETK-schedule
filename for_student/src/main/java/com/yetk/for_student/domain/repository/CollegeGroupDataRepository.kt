package com.yetk.for_student.domain.repository

import com.yetk.for_student.common.Response
import com.yetk.for_student.domain.model.BellSchedule
import com.yetk.for_student.domain.model.CollegeGroup
import kotlinx.coroutines.flow.Flow

interface CollegeGroupDataRepository {
    fun getCollegeGroupData(groupId: String): Flow<Response<CollegeGroup>>
    fun getBellSchedule(): Flow<Response<BellSchedule>>
    fun isLowerWeek(): Flow<Response<Boolean>>
}