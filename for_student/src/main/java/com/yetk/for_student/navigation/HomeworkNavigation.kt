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
    onNavigateToEditScreen:(homeworkId: Int, homeworkContent: String, homeworkSubject: String) -> Unit,
    onNavigateToAddScreen: () -> Unit,
) {
    composable(
        route = homeworkNavigationRoute,
    ) {
        HomeworkRoute(
            onNavigateToEditScreen = onNavigateToEditScreen,
            onNavigateToAddScreen = { onNavigateToAddScreen() },
            onShowSnackbar = onShowSnackbar
        )
    }
}