package com.yetk.forstudent.ui.screen.bellschedule

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yetk.forstudent.ui.screen.StudentViewModel

const val BELLS_NAV_ROUTE = "bells"

fun NavController.navigateToBellSchedule(navOptions: NavOptions? = null) {
    this.navigate(BELLS_NAV_ROUTE, navOptions)
}

fun NavGraphBuilder.bellScheduleScreen(studentViewModel: StudentViewModel) {
    composable(
        route = BELLS_NAV_ROUTE
    ) {
        BellScheduleRoute(studentViewModel.bellSchedule.collectAsStateWithLifecycle().value)
    }
}
