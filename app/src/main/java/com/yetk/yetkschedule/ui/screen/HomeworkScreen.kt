package com.yetk.yetkschedule.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lessonsschedulemanagerv2.ui.dragdrop.DragDropList
import com.yetk.yetkschedule.HomeworkEvent
import com.yetk.yetkschedule.HomeworkState
import com.yetk.yetkschedule.R
import com.yetk.yetkschedule.data.local.model.Homework
import com.yetk.yetkschedule.other.SwipeableSnackbarHost
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray90
import com.yetk.yetkschedule.ui.theme.Red
import com.yetk.yetkschedule.ui.theme.White
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import kotlinx.coroutines.launch

val homeworkList = mutableListOf(
    Homework(id = 0, content = "some home work ex 1 page 6", "Physics"),
    Homework(id = 1, content = "some home work ex 1 page 6", "Math"),
    Homework(id = 2, content = "some home work ex 1 page 6", "Science"),
    Homework(id = 3, content = "some home work ex 1 page 6", "PE"),
    Homework(
        id = 4,
        content = "some home work fdsaf sadfsadfasdfhj daskjlfhsadkljfh sadklfjhasldkjfhaskld hfaklsdjhflkasjhdf",
        "Physics"
    )
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeworkScreen(
    state: HomeworkState,
    onEvent: (HomeworkEvent) -> Unit,
    bottomBarPadding: PaddingValues,
    onNavigateToDetailScreen: (id: Int?) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.padding(bottomBarPadding),
        topBar = {
            Column() {
                TopAppBar(
                    modifier = Modifier.padding(end = 16.dp),
                    title = {
                        Text(
                            text = "Домашние задания",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    },
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp), thickness = 1.dp, color = Gray90
                )
            }
        },
        snackbarHost = {
            SwipeableSnackbarHost(snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateToDetailScreen(-1)
                },
                containerColor = White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add",
                    tint = Gray50
                )
            }
        },
    ) { topBarPadding ->
        DragDropList(
            modifier = Modifier
                .fillMaxSize()
                .padding(topBarPadding),
            dataItems = state.homeworks,
            onMove = { fromIndex, toIndex ->
                //TODO on move mutableList.move(fromIndex, toIndex)
            }
        ) { homework ->
            var isItemVisible by remember {
                mutableStateOf(true)
            }

            if(isItemVisible) {
                Column() {
                    HomeworkListItem(
                        homework,
                        onCheck = {
                            isItemVisible = false

                            scope.launch {
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Сделанное дз скрыто.",
                                        actionLabel = "Отмена",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        isItemVisible = true
                                    }

                                    SnackbarResult.Dismissed -> {
                                        onEvent(HomeworkEvent.DeleteHomework(homework = homework))
                                    }
                                }
                            }
                        },
                        onItemClick = { homeworkId ->
                            onNavigateToDetailScreen(homeworkId)
                        },
                        onBackgroundEndClick = { id ->
                            isItemVisible = false
                            //TODO make slide action close if other slide action is opened
                            scope.launch {
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Дз удалено.",
                                        actionLabel = "Отмена",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        isItemVisible = true
                                    }

                                    SnackbarResult.Dismissed -> {
                                        onEvent(HomeworkEvent.DeleteHomework(homework = homework))
                                    }
                                }
                            }
                        },
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp), thickness = 1.dp, color = Gray90
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeworkListItem(
    homework: Homework,
    onBackgroundEndClick: (id: Int) -> Unit,
    onItemClick: (id: Int) -> Unit,
    onCheck: () -> Unit
) {
    var isChecked by remember {
        mutableStateOf(false)
    }

    RevealSwipe(
        modifier = Modifier
            .fillMaxWidth(),
        directions = setOf(
            RevealDirection.EndToStart
        ),

        backgroundCardEndColor = Red,
        hiddenContentEnd = {
            androidx.compose.material.Icon(
                modifier = Modifier.padding(horizontal = 25.dp),
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = null,
                tint = White
            )
        },
        onBackgroundEndClick = {
            onBackgroundEndClick(homework.id)
        }
    ) { shape ->
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = White),
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        onItemClick(homework.id)
                    }
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text(
                        text = homework.subjectName.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = homework.content.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = !isChecked
                        if (isChecked) {
                            onCheck()
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.secondary,
                    ),
                )
            }
        }
    }
}


