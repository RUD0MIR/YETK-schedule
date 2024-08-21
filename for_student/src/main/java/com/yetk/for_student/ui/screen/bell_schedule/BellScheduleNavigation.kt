package com.yetk.for_student.ui.screen.bell_schedule

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yetk.for_student.common.Response
import com.yetk.for_student.domain.model.BellSchedule
import com.yetk.for_student.ui.screen.StudentViewModel

const val bellsNavigationRoute = "bells"

fun NavController.navigateToBellSchedule(navOptions: NavOptions? = null) {
    this.navigate(bellsNavigationRoute, navOptions)
}

fun NavGraphBuilder.bellScheduleScreen(
    studentViewModel: StudentViewModel
) {
    composable(
        route = bellsNavigationRoute,
    ) {
        BellScheduleRoute(studentViewModel.bellSchedule.collectAsStateWithLifecycle().value)
    }
}