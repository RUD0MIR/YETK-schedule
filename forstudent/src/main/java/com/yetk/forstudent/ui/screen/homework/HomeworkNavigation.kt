package com.yetk.forstudent.ui.screen.homework

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HW_NAV_ROUTE = "homework"

fun NavController.navigateToHomework(navOptions: NavOptions? = null) {
    this.navigate(HW_NAV_ROUTE, navOptions)
}

fun NavGraphBuilder.homeworkScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: HomeworkViewModel,
    subjectNames: List<String>
) {
    composable(
        route = HW_NAV_ROUTE
    ) {
        HomeworkRoute(
            homeworks = viewModel.homeworks.collectAsStateWithLifecycle().value,
            onShowSnackbar = onShowSnackbar,
            onHomeworkDelete = { viewModel.deleteHomework(it) },
            onHomeworkCheck = { viewModel.checkHomework(it) },
            subjectNames = subjectNames,
            onInputCheck = viewModel::checkCorrectInput,
            onHomeworkInsert = viewModel::insertHomework,
            onHomeworkUpdate = viewModel::updateHomework
        )
    }
}
