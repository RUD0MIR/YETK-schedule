package com.yetk.for_student.ui.screen.bell_schedule

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val bellsNavigationRoute = "bells"

fun NavController.navigateToBellSchedule(navOptions: NavOptions? = null) {
    this.navigate(bellsNavigationRoute, navOptions)
}

fun NavGraphBuilder.bellScheduleScreen(
) {
    composable(
        route = bellsNavigationRoute,
    ) {
        BellScheduleRoute()
    }
}