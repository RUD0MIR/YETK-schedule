package com.yetk.for_student.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yetk.for_student.screen.BellScheduleRoute

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