package com.yetk.for_student

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.yetk.for_student.navigation.TopLevelDestination
import com.yetk.for_student.navigation.bellScheduleNavigationRoute
import com.yetk.for_student.navigation.homeworkNavigationRoute
import com.yetk.for_student.navigation.navigateToBellSchedule
import com.yetk.for_student.navigation.navigateToHomework
import com.yetk.for_student.navigation.navigateToSchedule
import com.yetk.for_student.navigation.navigateToSubjects
import com.yetk.for_student.navigation.scheduleNavigationRoute
import com.yetk.for_student.navigation.subjectsNavigationRoute
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberStudentAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): StudentAppState {
    return remember(
        navController,
        coroutineScope,
    ) {
        StudentAppState(
            navController,
            coroutineScope,
        )
    }
}

@Stable
class StudentAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            scheduleNavigationRoute -> TopLevelDestination.SCHEDULE
            homeworkNavigationRoute -> TopLevelDestination.HOMEWORK
            bellScheduleNavigationRoute -> TopLevelDestination.BELL_SCHEDULE
            subjectsNavigationRoute -> TopLevelDestination.SUBJECTS
            else -> null
        }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.SCHEDULE -> navController.navigateToSchedule(topLevelNavOptions)
                TopLevelDestination.HOMEWORK -> navController.navigateToHomework(topLevelNavOptions)
                TopLevelDestination.BELL_SCHEDULE -> navController.navigateToBellSchedule(topLevelNavOptions)
                TopLevelDestination.SUBJECTS -> navController.navigateToSubjects(topLevelNavOptions)
                else -> {}
            }
        }
    }
}
