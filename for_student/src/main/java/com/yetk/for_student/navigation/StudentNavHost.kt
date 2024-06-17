package com.yetk.for_student.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.yetk.for_student.StudentAppState
import com.yetk.for_student.data.remote.viewmodel.StudentViewModel

@Composable
fun StudentNavHost(
    appState: StudentAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = scheduleNavigationRoute, //TODO authorizationNavigationRoute
    studentViewModel: StudentViewModel = hiltViewModel()
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        authorizationScreen(navController::navigateToSchedule)
        scheduleScreen(studentViewModel)
        homeworkScreen(
            onShowSnackbar = onShowSnackbar,
            onNavigateToAddScreen = { navController.navigateToHomeworkDetail() },
            onNavigateToEditScreen = { id, content, subjectName ->
                navController.navigateToHomeworkDetail(id, content, subjectName)
            }
        )
        bellScheduleScreen()
        subjectsScreen()
        homeworkDetailScreen(
            subjectsNames = studentViewModel.getSubjectsNames(),
            onNavigateUp = navController::popBackStack,
        )
    }
}