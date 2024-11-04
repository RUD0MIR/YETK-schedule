package com.yetk.forstudent.ui.screen.subjects

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val SUBJECTS_NAV_ROUTE = "subjects"

fun NavController.navigateToSubjects(navOptions: NavOptions? = null) {
    this.navigate(SUBJECTS_NAV_ROUTE, navOptions)
}

fun NavGraphBuilder.subjectsScreen() {
    composable(
        route = SUBJECTS_NAV_ROUTE
    ) {
        SubjectsRoute()
    }
}
