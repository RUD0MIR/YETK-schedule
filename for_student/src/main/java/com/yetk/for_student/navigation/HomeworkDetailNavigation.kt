package com.yetk.for_student.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yetk.for_student.data.local.viewmodel.HomeworkViewModel
import com.yetk.for_student.screen.HomeworkDetailRoute

const val homeworkIdArg = "homeworkId"

fun NavController.navigateToHomeworkDetail() {
    this.navigate("homeworkDetail") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.homeworkDetailScreen(
    onNavigateUp: () -> Unit,
    navController: NavController
) {
    composable(
        route = "homeworkDetail/{$homeworkIdArg}",
        arguments = listOf(
            navArgument(homeworkIdArg) { type = NavType.IntType },
        ),
    ) {
        val parentEntry = remember(it) {
            navController.getBackStackEntry(homeworkNavigationRoute)
        }
        val viewModel = hiltViewModel<HomeworkViewModel>(parentEntry)

        HomeworkDetailRoute(
            onNavigateUp = onNavigateUp,
            viewModel = viewModel
        )
    }
}