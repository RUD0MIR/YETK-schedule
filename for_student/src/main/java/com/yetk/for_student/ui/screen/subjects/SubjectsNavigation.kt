package com.yetk.for_student.ui.screen.subjects

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val subjectsNavigationRoute = "subjects"

fun NavController.navigateToSubjects(navOptions: NavOptions? = null) {
    this.navigate(subjectsNavigationRoute, navOptions)
}

fun NavGraphBuilder.subjectsScreen() {
    composable(
        route = subjectsNavigationRoute,
    ) {
        SubjectsRoute()
    }
}