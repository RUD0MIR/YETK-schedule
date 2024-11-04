package com.yetk.forstudent.ui.screen.schedule

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yetk.forstudent.ui.screen.StudentViewModel

const val SCHEDULE_NAV_ROUTE = "schedule"

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) {
    this.navigate(SCHEDULE_NAV_ROUTE, navOptions)
}

fun NavGraphBuilder.scheduleScreen(studentViewModel: StudentViewModel) {
    composable(
        route = SCHEDULE_NAV_ROUTE
    ) {
        ScheduleRoute(
            collegeGroup = studentViewModel.collegeGroup.collectAsStateWithLifecycle().value,
            isLowerWeek = studentViewModel.isLowerWeek.collectAsStateWithLifecycle().value,
            bellSchedule = studentViewModel.bellSchedule.collectAsStateWithLifecycle().value
        )
    }
}
