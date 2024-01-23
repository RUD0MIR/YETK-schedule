package com.yetk.yetkschedule.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yetk.yetkschedule.R
import com.yetk.yetkschedule.data.local.viewmodel.HomeworkEvent
import com.yetk.yetkschedule.data.local.viewmodel.HomeworkViewModel
import com.yetk.yetkschedule.other.BottomNavigationItem
import com.yetk.yetkschedule.other.NoRippleInteractionSource
import com.yetk.yetkschedule.other.Screen
import com.yetk.yetkschedule.other.shadow
import com.yetk.yetkschedule.ui.screen.BellScheduleScreen
import com.yetk.yetkschedule.ui.screen.HomeworkDetailScreen
import com.yetk.yetkschedule.ui.screen.HomeworkScreen
import com.yetk.yetkschedule.ui.screen.ScheduleScreen
import com.yetk.yetkschedule.ui.screen.SubjectsScreen
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray80
import com.yetk.yetkschedule.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NavHost(viewModel: HomeworkViewModel) {
    val state = viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showBottomBar by remember {
        mutableStateOf(true)
    }
    val navController = rememberNavController()
    val bottomBarItems = listOf(
        BottomNavigationItem(
            title = "Lessons",
            icon = painterResource(id = R.drawable.ic_date_range),
            route = Screen.ScheduleScreen.route
        ),
        BottomNavigationItem(
            title = "Homework",
            icon = painterResource(id = R.drawable.ic_home),
            route = Screen.HomeworkScreen.route
        ),
        BottomNavigationItem(
            title = "Bell schedule",
            icon = painterResource(id = R.drawable.ic_notification),
            route = Screen.BellScreen.route
        ), BottomNavigationItem(
            title = "Subjects",
            icon = painterResource(id = R.drawable.ic_subject),
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
                                        painter = item.icon,
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
                    ScheduleScreen(
                        PaddingValues(bottom = 60.dp)
                    )
                }
                composable(
                    route = Screen.HomeworkScreen.route,
                ) {
                    HomeworkScreen(
                        state = state.value,
                        onEvent = viewModel::onEvent,
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
                                viewModel.onEvent(HomeworkEvent.HideHomework(homework))
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Дз удалено.",
                                        actionLabel = "Отмена",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        viewModel.onEvent(HomeworkEvent.ShowHomework(homework))
                                    }

                                    SnackbarResult.Dismissed -> {
                                        viewModel.onEvent(HomeworkEvent.DeleteHomework(homework = homework))
                                    }
                                }
                            }
                        },
                        onHomeworkCheck = { homework ->
                            scope.launch {
                                delay(400)
                                viewModel.onEvent(HomeworkEvent.HideHomework(homework))
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Сделанное дз скрыто.",
                                        actionLabel = "Отмена",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        viewModel.onEvent(HomeworkEvent.ShowHomework(homework))
                                    }

                                    SnackbarResult.Dismissed -> {
                                        viewModel.onEvent(HomeworkEvent.DeleteHomework(homework = homework))
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
                    HomeworkDetailScreen(
                        homeworkId,
                        state = state.value,
                        onEvent = viewModel::onEvent,
                        onNavigateUp = {
                            navController.navigateUp()
                            showBottomBar = true
                        },
                        onHomeworkDelete = { homework ->
                            scope.launch {
                                viewModel.onEvent(HomeworkEvent.HideHomework(homework))
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Дз удалено.",
                                        actionLabel = "Отмена",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        viewModel.onEvent(HomeworkEvent.ShowHomework(homework))
                                    }

                                    SnackbarResult.Dismissed -> {
                                        viewModel.onEvent(HomeworkEvent.DeleteHomework(homework = homework))
                                    }
                                }
                            }
                        },
                        onHomeworkCheck = { homework ->
                            scope.launch {
                                delay(400)
                                viewModel.onEvent(HomeworkEvent.HideHomework(homework))
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Сделанное дз скрыто.",
                                        actionLabel = "Отмена",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        viewModel.onEvent(HomeworkEvent.ShowHomework(homework))
                                    }

                                    SnackbarResult.Dismissed -> {
                                        viewModel.onEvent(HomeworkEvent.DeleteHomework(homework = homework))
                                    }
                                }
                            }
                        }
                    )
                }
                composable(route = Screen.BellScreen.route) {
                    BellScheduleScreen(PaddingValues(bottom = 60.dp))
                }
                composable(route = Screen.SubjectsScreen.route) {
                    SubjectsScreen(
                        PaddingValues(bottom = 60.dp)
                    )
                }
            }
            unusedPadding
        }
    }
}