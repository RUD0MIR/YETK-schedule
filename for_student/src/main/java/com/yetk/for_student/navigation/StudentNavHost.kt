package com.yetk.for_student.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.yetk.for_student.StudentAppState

@Composable
fun StudentNavHost(
    appState: StudentAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = scheduleNavigationRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        scheduleScreen()
        homeworkScreen(
            onShowSnackbar = onShowSnackbar,
            onNavigateToDetailScreen = {},
        )
    }
}