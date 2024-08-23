package com.yetk.for_student

import com.yetk.for_student.common.Response
import com.yetk.for_student.domain.repository.CollegeGroupDataRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class FakeCollegeGroupRepoImpl: CollegeGroupDataRepository {

    override fun getCollegeGroupData(groupId: String) = flow {
        emit(Response.Loading)
        delay(DELAY)
        emit(
            Response.Success(
                TestData.collegeGroup1
            )
        )
    }

    override fun getBellSchedule() = flow {
        emit(Response.Loading)
        delay(DELAY)
        emit(
            Response.Success(
                TestData.bellSchedule
            )
        )
    }

    override fun isLowerWeek() = flow {
        emit(Response.Loading)
        delay(DELAY)
        emit(
            Response.Success(
                true
            )
        )
    }

    companion object {
        const val DELAY = 3000L
    }
}