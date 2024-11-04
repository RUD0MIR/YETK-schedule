package com.yetk.forstudent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.yetk.forstudent.StudentAppState
import com.yetk.forstudent.ui.screen.StudentViewModel
import com.yetk.forstudent.ui.screen.auth.authorizationScreen
import com.yetk.forstudent.ui.screen.bellschedule.bellScheduleScreen
import com.yetk.forstudent.ui.screen.homework.HomeworkViewModel
import com.yetk.forstudent.ui.screen.homework.homeworkScreen
import com.yetk.forstudent.ui.screen.schedule.SCHEDULE_NAV_ROUTE
import com.yetk.forstudent.ui.screen.schedule.navigateToSchedule
import com.yetk.forstudent.ui.screen.schedule.scheduleScreen
import com.yetk.forstudent.ui.screen.subjects.subjectsScreen

@Composable
fun StudentNavHost(
    appState: StudentAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    // AUTH_NAV_ROUTE
    startDestination: String = SCHEDULE_NAV_ROUTE,
    studentViewModel: StudentViewModel = hiltViewModel(),
    homeworkViewModel: HomeworkViewModel = hiltViewModel()
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        authorizationScreen(navController::navigateToSchedule)
        scheduleScreen(studentViewModel)
        homeworkScreen(
            onShowSnackbar = onShowSnackbar,
            viewModel = homeworkViewModel,
            subjectNames = studentViewModel.getSubjectsNames()
        )
        bellScheduleScreen(studentViewModel)
        subjectsScreen()
    }
}
