package com.yetk.for_student.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.yetk.for_student.StudentAppState
import com.yetk.for_student.ui.screen.homework.HomeworkViewModel
import com.yetk.for_student.ui.screen.StudentViewModel
import com.yetk.for_student.ui.screen.auth.authorizationScreen
import com.yetk.for_student.ui.screen.bell_schedule.bellScheduleScreen
import com.yetk.for_student.ui.screen.homework.homeworkScreen
import com.yetk.for_student.ui.screen.schedule.navigateToSchedule
import com.yetk.for_student.ui.screen.schedule.scheduleNavigationRoute
import com.yetk.for_student.ui.screen.schedule.scheduleScreen
import com.yetk.for_student.ui.screen.subjects.subjectsScreen

@Composable
fun StudentNavHost(
    appState: StudentAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = scheduleNavigationRoute, //TODO authorizationNavigationRoute
    studentViewModel: StudentViewModel = hiltViewModel(),
    homeworkViewModel: HomeworkViewModel = hiltViewModel()
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
            viewModel = homeworkViewModel,
            subjectNames = studentViewModel.getSubjectsNames()
        )
        bellScheduleScreen()
        subjectsScreen()

//        homeworkDetailScreen(
//            subjectsNames = studentViewModel.getSubjectsNames(),
//            onNavigateUp = navController::popBackStack,
//            homeworkViewModel
//        )
    }
}