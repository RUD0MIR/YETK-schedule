package com.yetk.for_student.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yetk.for_student.screen.HomeworkDetailRoute

const val homeworkIdArg = "homeworkId"
const val homeworkContentArg = "homeworkContent"
const val homeworkSubjectArg = "homeworkSubject"
const val hwDetailNavigationRoute = "homeworkDetail/{${homeworkIdArg}}/{${homeworkContentArg}}/{${homeworkSubjectArg}}"


fun NavController.navigateToHomeworkDetail(
    homeworkId: Int = -1,
    homeworkContent: String = " ",
    homeworkSubject: String = " "
) {
    this.navigate("homeworkDetail/${homeworkId}/${homeworkContent}/${homeworkSubject}") {
        launchSingleTop = true

    }
}

fun NavGraphBuilder.homeworkDetailScreen(
    subjectsNames: List<String>,
    onNavigateUp: () -> Unit,
) {
    composable(
        route = hwDetailNavigationRoute,
        arguments = listOf(
            navArgument(homeworkIdArg) { type = NavType.IntType },
            navArgument(homeworkContentArg) { type = NavType.StringType },
            navArgument(homeworkSubjectArg) { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        HomeworkDetailRoute(
            subjectsNames = subjectsNames,
            homeworkId = navBackStackEntry.arguments?.getInt(homeworkIdArg) ?: -1,
            homeworkContent = navBackStackEntry.arguments?.getString(homeworkContentArg) ?: "??",
            homeworkSubject = navBackStackEntry.arguments?.getString(homeworkSubjectArg) ?: "??",
            onNavigateUp = onNavigateUp
        )
    }
}