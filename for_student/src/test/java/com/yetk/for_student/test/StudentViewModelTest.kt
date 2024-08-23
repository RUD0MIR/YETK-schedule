package com.yetk.for_student.test

import com.yetk.for_student.FakeCollegeGroupRepoImpl
import com.yetk.for_student.MainDispatcherRule
import com.yetk.for_student.common.Response
import com.yetk.for_student.TestData
import com.yetk.for_student.ui.screen.StudentViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StudentViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `vm returns correct bellSchedule`() = runTest {
        val vm = StudentViewModel(FakeCollegeGroupRepoImpl())
        advanceUntilIdle()

        assertEquals(
            TestData.bellSchedule,
            (vm.bellSchedule.value as Response.Success).data
        )
    }

    @Test
    fun `vm returns correct collegeGroup`() = runTest {
        val vm = StudentViewModel(FakeCollegeGroupRepoImpl())
        advanceUntilIdle()

        assertEquals(
            TestData.collegeGroup1,
            (vm.collegeGroup.value as Response.Success).data
        )
    }

    @Test
    fun `vm returns correct isLowerWeek value`() = runTest {
        val vm = StudentViewModel(FakeCollegeGroupRepoImpl())
        advanceUntilIdle()

        assertEquals(
            true,
            (vm.isLowerWeek.value as Response.Success).data
        )
    }
}