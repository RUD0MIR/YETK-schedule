package com.yetk.for_student.ui.screen.schedule

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yetk.for_student.ui.screen.StudentViewModel

const val scheduleNavigationRoute = "schedule"

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) {
    this.navigate(scheduleNavigationRoute, navOptions)
}

fun NavGraphBuilder.scheduleScreen(studentViewModel: StudentViewModel) {
    composable(
        route = scheduleNavigationRoute,
    ) {
        ScheduleRoute(
            collegeGroup = studentViewModel.collegeGroup.collectAsStateWithLifecycle().value,
            isLowerWeek = studentViewModel.isLowerWeek.collectAsStateWithLifecycle().value,
            bellSchedule = studentViewModel.bellSchedule.collectAsStateWithLifecycle().value
        )
    }
}