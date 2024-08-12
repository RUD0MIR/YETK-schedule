package com.yetk.for_student.navigation.destination

import android.util.Log
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yetk.for_student.common.Const.NULL_ID
import com.yetk.for_student.common.DetailScreenType
import com.yetk.for_student.data.local.HomeworkViewModel
import com.yetk.for_student.screen.HomeworkDetailRoute

const val homeworkIdArg = "homeworkId"
const val homeworkContentArg = "homeworkContent"
const val homeworkSubjectArg = "homeworkSubject"
const val hwDetailNavigationRoute = "homeworkDetail/{$homeworkIdArg}/{$homeworkContentArg}/{$homeworkSubjectArg}"


fun NavController.navigateToHomeworkDetail(
    homeworkId: Int = NULL_ID,
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
    viewModel: HomeworkViewModel
) {
    composable(
        route = hwDetailNavigationRoute,
        arguments = listOf(
            navArgument(homeworkIdArg) { type = NavType.IntType },
            navArgument(homeworkContentArg) { type = NavType.StringType },
            navArgument(homeworkSubjectArg) { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        val homeworkId = navBackStackEntry.arguments?.getInt(homeworkIdArg) ?: NULL_ID

        val detailType: DetailScreenType =
            if (homeworkId != NULL_ID) DetailScreenType.EditScreen(homeworkId)
            else DetailScreenType.AddScreen

        HomeworkDetailRoute(
            subjectsNames = subjectsNames,
            detailScreenType = detailType,
            onHomeworkCheck = viewModel::checkHomework,
            onHomeworkDelete = viewModel::deleteHomework,
            onHomeworkInsert = viewModel::insertHomework,
            onHomeworkUpdate = viewModel::updateHomework,
            onNavigateUp = onNavigateUp,
            checkCorrectInput = viewModel::checkCorrectInput,
            homeworkSubject = navBackStackEntry.arguments?.getString(homeworkSubjectArg) ?: "",
            homeworkContent =  navBackStackEntry.arguments?.getString(homeworkContentArg) ?: ""
        )
    }
}