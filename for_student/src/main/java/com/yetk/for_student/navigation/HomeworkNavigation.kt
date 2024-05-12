package com.yetk.for_student.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yetk.for_student.screen.HomeworkRoute

const val homeworkNavigationRoute = "homework"

fun NavController.navigateToHomework(navOptions: NavOptions? = null) {
    this.navigate(homeworkNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeworkScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onNavigateToEditScreen: (id: Int) -> Unit,
    onNavigateToAddScreen: (id: Int) -> Unit,
) {
    composable(
        route = homeworkNavigationRoute,
    ) {
        HomeworkRoute(
            onShowSnackbar = onShowSnackbar,
            onNavigateToEditScreen = onNavigateToEditScreen,
            onNavigateToAddScreen = { onNavigateToAddScreen(-1) }
        )
    }
}