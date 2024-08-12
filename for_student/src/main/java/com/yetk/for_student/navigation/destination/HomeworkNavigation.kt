package com.yetk.for_student.navigation.destination

import android.util.Log
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yetk.for_student.data.local.HomeworkViewModel
import com.yetk.for_student.screen.HomeworkRoute

const val homeworkNavigationRoute = "homework"

fun NavController.navigateToHomework(navOptions: NavOptions? = null) {
    this.navigate(homeworkNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeworkScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: HomeworkViewModel,
    onNavigateToEditScreen:(homeworkId: Int, homeworkContent: String, homeworkSubject: String) -> Unit,
    onNavigateToAddScreen: () -> Unit,
) {
    composable(
        route = homeworkNavigationRoute,
    ) {
        HomeworkRoute(
            homeworks = viewModel.homeworks.collectAsStateWithLifecycle().value,
            onNavigateToEditScreen = onNavigateToEditScreen,
            onNavigateToAddScreen = { onNavigateToAddScreen() },
            onShowSnackbar = onShowSnackbar,
            onHomeworkDelete = { viewModel.deleteHomework(it) },
            onHomeworkCheck = { viewModel.checkHomework(it) },
        )
    }
}