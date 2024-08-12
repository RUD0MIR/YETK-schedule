package com.yetk.for_student.navigation.destination

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yetk.for_student.data.remote.viewmodel.StudentViewModel
import com.yetk.for_student.screen.ScheduleRoute

const val scheduleNavigationRoute = "schedule"

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) {
    this.navigate(scheduleNavigationRoute, navOptions)
}

fun NavGraphBuilder.scheduleScreen(studentViewModel: StudentViewModel) {
    composable(
        route = scheduleNavigationRoute,
    ) {
        ScheduleRoute(studentViewModel)
    }
}