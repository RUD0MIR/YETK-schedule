package com.yetk.for_student.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yetk.for_student.screen.HomeworkDetailRoute

const val homeworkIdArg = "homeworkId"

fun NavController.navigateToHomeworkDetail(homeworkId: Int) {
    this.navigate("homeworkDetail/$homeworkId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.homeworkDetailScreen(
    onNavigateUp: () -> Unit
) {
    composable(
        route = "homeworkDetail/{$homeworkIdArg}",
        arguments = listOf(
            navArgument(homeworkIdArg) { type = NavType.IntType },
        ),
    ) {
        HomeworkDetailRoute(onNavigateUp = onNavigateUp)
    }
}