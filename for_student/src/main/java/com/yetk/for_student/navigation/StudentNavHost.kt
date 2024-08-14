package com.yetk.for_student.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.yetk.for_student.StudentAppState
import com.yetk.for_student.data.local.HomeworkViewModel
import com.yetk.for_student.data.remote.viewmodel.StudentViewModel
import com.yetk.for_student.navigation.destination.authorizationScreen
import com.yetk.for_student.navigation.destination.bellScheduleScreen
import com.yetk.for_student.navigation.destination.homeworkDetailScreen
import com.yetk.for_student.navigation.destination.homeworkNavigationRoute
import com.yetk.for_student.navigation.destination.homeworkScreen
import com.yetk.for_student.navigation.destination.navigateToHomeworkDetail
import com.yetk.for_student.navigation.destination.navigateToSchedule
import com.yetk.for_student.navigation.destination.scheduleNavigationRoute
import com.yetk.for_student.navigation.destination.scheduleScreen
import com.yetk.for_student.navigation.destination.subjectsScreen

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