package com.yetk.for_student

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yetk.designsystem.theme.Gray50
import com.yetk.designsystem.theme.Gray80
import com.yetk.for_student.data.local.viewmodel.HomeworkViewModel
import com.yetk.model.BottomNavigationItem
import com.yetk.ui.NoRippleInteractionSource
import com.yetk.ui.shadow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StudentNavigation(
    homeworkViewModel: HomeworkViewModel = hiltViewModel()
) {
    val homeworkState = homeworkViewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showBottomBar by remember {
        mutableStateOf(true)
    }
    val navController = rememberNavController()
    val bottomBarItems = listOf(
        BottomNavigationItem(
            title = "Lessons",
            icon =Icons.Filled.DateRange,
            route = Screen.ScheduleScreen.route
        ),
        BottomNavigationItem(
            title = "Homework",
            icon = Icons.Filled.Home,
            route = Screen.HomeworkScreen.route
        ),
        BottomNavigationItem(
            title = "Bell schedule",
            icon = Icons.Filled.Notifications,
            route = Screen.BellScreen.route
        ), BottomNavigationItem(
            title = "Subjects",
            icon = Icons.Filled.List,
            route = Screen.SubjectsScreen.route
        )
    )
    var selectedBottomBarItemIndex by remember {
        mutableIntStateOf(0)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Scaffold(
            bottomBar = {
                if(showBottomBar) {
                    NavigationBar(
                        modifier = Modifier
                            .height(60.dp)
                            .shadow(
                                shape = RectangleShape,
                                blurRadius = 20.dp,
                                color = Gray80,
                                offsetX = 0.dp,
                                offsetY = (6).dp
                            ),
                        containerColor = White
                    ) {
                        bottomBarItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedBottomBarItemIndex == index,
                                onClick = {
                                    selectedBottomBarItemIndex = index
                                    navController.navigate(item.route)
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Gray50,
                                    indicatorColor = White,
                                    unselectedIconColor = Gray80,
                                ),
                                interactionSource = NoRippleInteractionSource(),
                                icon = {
                                    Icon(
                                        modifier = Modifier.size(32.dp),
                                        imageVector = item.icon,
                                        contentDescription = item.title
                                    )
                                }
                            )
                        }
                    }
                }
            }
        ) { unusedPadding ->
            androidx.navigation.compose.NavHost(
                navController = navController,
                startDestination = Screen.ScheduleScreen.route
            ) {
                composable(route = Screen.ScheduleScreen.route) {
                    com.yetk.for_student.screen.ScheduleScreen(
                        PaddingValues(bottom = 60.dp)
                    )
                }
                composable(
                    route = Screen.HomeworkScreen.route,
                ) {
                    com.yetk.for_student.screen.HomeworkScreen(
                        state = homeworkState.value,
                        onEvent = homeworkViewModel::onEvent,
                        onNavigateToDetailScreen = { homeworkId ->
                            navController.navigate(
                                Screen.HomeworkScreen.HomeworkDetailScreen.passArgs(
                                    "$homeworkId"
                                )
                            )
                            showBottomBar = false
                        },
                        snackbarHostState = snackbarHostState,
                        bottomBarPadding = PaddingValues(bottom = 60.dp),
                        onHomeworkDelete = { homework ->
                            scope.launch {
                                homeworkViewModel.onEvent(
                                    com.yetk.for_student.data.local.viewmodel.HomeworkEvent.HideHomework(
                                        homework
                                    )
                                )
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Дз удалено.",
                                        actionLabel = "Отмена",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        homeworkViewModel.onEvent(
                                            com.yetk.for_student.data.local.viewmodel.HomeworkEvent.ShowHomework(
                                                homework
                                            )
                                        )
                                    }

                                    SnackbarResult.Dismissed -> {
                                        homeworkViewModel.onEvent(
                                            com.yetk.for_student.data.local.viewmodel.HomeworkEvent.DeleteHomework(
                                                homework = homework
                                            )
                                        )
                                    }
                                }
                            }
                        },
                        onHomeworkCheck = { homework ->
                            scope.launch {
                                delay(400)
                                homeworkViewModel.onEvent(
                                    com.yetk.for_student.data.local.viewmodel.HomeworkEvent.HideHomework(
                                        homework
                                    )
                                )
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Сделанное дз скрыто.",
                                        actionLabel = "Отмена",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        homeworkViewModel.onEvent(
                                            com.yetk.for_student.data.local.viewmodel.HomeworkEvent.ShowHomework(
                                                homework
                                            )
                                        )
                                    }

                                    SnackbarResult.Dismissed -> {
                                        homeworkViewModel.onEvent(
                                            com.yetk.for_student.data.local.viewmodel.HomeworkEvent.DeleteHomework(
                                                homework = homework
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
                composable(
                    route = Screen.HomeworkScreen.HomeworkDetailScreen.withArgs("homeworkId"),
                    arguments = listOf(navArgument("homeworkId") {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    val homeworkId = backStackEntry.arguments?.getInt("homeworkId") ?: -1
                    com.yetk.for_student.screen.HomeworkDetailScreen(
                        homeworkId,
                        state = homeworkState.value,
                        onEvent = homeworkViewModel::onEvent,
                        onNavigateUp = {
                            navController.navigateUp()
                            showBottomBar = true
                        },
                        onHomeworkDelete = { homework ->
                            scope.launch {
                                homeworkViewModel.onEvent(
                                    com.yetk.for_student.data.local.viewmodel.HomeworkEvent.HideHomework(
                                        homework
                                    )
                                )
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Дз удалено.",
                                        actionLabel = "Отмена",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        homeworkViewModel.onEvent(
                                            com.yetk.for_student.data.local.viewmodel.HomeworkEvent.ShowHomework(
                                                homework
                                            )
                                        )
                                    }

                                    SnackbarResult.Dismissed -> {
                                        homeworkViewModel.onEvent(
                                            com.yetk.for_student.data.local.viewmodel.HomeworkEvent.DeleteHomework(
                                                homework = homework
                                            )
                                        )
                                    }
                                }
                            }
                        },
                        onHomeworkCheck = { homework ->
                            scope.launch {
                                delay(400)
                                homeworkViewModel.onEvent(
                                    com.yetk.for_student.data.local.viewmodel.HomeworkEvent.HideHomework(
                                        homework
                                    )
                                )
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Сделанное дз скрыто.",
                                        actionLabel = "Отмена",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        homeworkViewModel.onEvent(
                                            com.yetk.for_student.data.local.viewmodel.HomeworkEvent.ShowHomework(
                                                homework
                                            )
                                        )
                                    }

                                    SnackbarResult.Dismissed -> {
                                        homeworkViewModel.onEvent(
                                            com.yetk.for_student.data.local.viewmodel.HomeworkEvent.DeleteHomework(
                                                homework = homework
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
                composable(route = Screen.BellScreen.route) {
                    com.yetk.for_student.screen.BellScheduleScreen(
                        bottomBarPadding = PaddingValues(
                            bottom = 60.dp
                        )
                    )
                }
                composable(route = Screen.SubjectsScreen.route) {
                    com.yetk.for_student.screen.SubjectsScreen(
                        PaddingValues(bottom = 60.dp)
                    )
                }
            }
            unusedPadding
        }
    }
}