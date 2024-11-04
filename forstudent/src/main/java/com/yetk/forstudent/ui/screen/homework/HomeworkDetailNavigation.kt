package com.yetk.forstudent.ui.screen.homework

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yetk.forstudent.common.Const.NULL_ID
import com.yetk.forstudent.common.DetailScreenType

const val HW_ID_ARG = "homeworkId"
const val HW_CONTENT_ARG = "homeworkContent"
const val HW_SUBJECT_ARG = "homeworkSubject"
const val HW_DETAIL_NAV_ROUTE = "homeworkDetail/{$HW_ID_ARG}/{$HW_CONTENT_ARG}/{$HW_SUBJECT_ARG}"

fun NavController.navigateToHomeworkDetail(
    homeworkId: Int = NULL_ID,
    homeworkContent: String = " ",
    homeworkSubject: String = " "
) {
    this.navigate("homeworkDetail/$homeworkId/$homeworkContent/$homeworkSubject") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.homeworkDetailScreen(
    subjectsNames: List<String>,
    onNavigateUp: () -> Unit,
    viewModel: HomeworkViewModel
) {
    composable(
        route = HW_DETAIL_NAV_ROUTE,
        arguments =
        listOf(
            navArgument(HW_ID_ARG) { type = NavType.IntType },
            navArgument(HW_CONTENT_ARG) { type = NavType.StringType },
            navArgument(HW_SUBJECT_ARG) { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        val homeworkId = navBackStackEntry.arguments?.getInt(HW_ID_ARG) ?: NULL_ID

        val detailType: DetailScreenType =
            if (homeworkId != NULL_ID) {
                DetailScreenType.EditScreen(homeworkId)
            } else {
                DetailScreenType.AddScreen
            }

        HomeworkDetailRoute(
            subjectsNames = subjectsNames,
            detailScreenType = detailType,
            onHomeworkCheck = viewModel::checkHomework,
            onHomeworkDelete = viewModel::deleteHomework,
            onHomeworkInsert = viewModel::insertHomework,
            onHomeworkUpdate = viewModel::updateHomework,
            onNavigateUp = onNavigateUp,
            checkCorrectInput = viewModel::checkCorrectInput,
            homeworkSubject = navBackStackEntry.arguments?.getString(HW_SUBJECT_ARG) ?: "",
            homeworkContent = navBackStackEntry.arguments?.getString(HW_CONTENT_ARG) ?: ""
        )
    }
}
