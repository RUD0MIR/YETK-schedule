package com.example.lessonsschedulemanagerv2.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yetk.yetkschedule.R
import com.example.lessonsschedulemanagerv2.data.local.model.LessonTime
import com.yetk.yetkschedule.other.SwipeableSnackbarHost
import com.yetk.yetkschedule.other.parseNhNmin
import com.yetk.yetkschedule.ui.TimePickerDialog
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray60
import com.yetk.yetkschedule.ui.theme.Gray70
import com.yetk.yetkschedule.ui.theme.Gray80
import com.yetk.yetkschedule.ui.theme.Gray90
import com.yetk.yetkschedule.ui.theme.Green
import com.yetk.yetkschedule.ui.theme.White
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

private val lessonTimes = listOf(
    LessonTime(id = 0, number = 1, startTime = "08:00", endTime = "09:40"),
    LessonTime(id = 1, number = 2, startTime = "08:00", endTime = "09:40"),
    LessonTime(id = 2, number = 3, startTime = "08:00", endTime = "09:40"),
    LessonTime(id = 3, number = 4, startTime = "08:00", endTime = "09:40"),
    LessonTime(id = 4, number = 5, startTime = "08:00", endTime = "09:40"),
    LessonTime(id = 5, number = 6, startTime = "08:00", endTime = "09:40"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BellScheduleScreen(bottomBarPadding: PaddingValues) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val lessonDuration by remember {
        mutableStateOf(Duration.ZERO.plus(30L.minutes))
    }

    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    Scaffold(
        modifier = Modifier.padding(bottomBarPadding),
        topBar = {
            Column() {
                TopAppBar(
                    modifier = Modifier.padding(end = 16.dp),
                    title = {
                        Text(
                            text = "Bell schedule",
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
                    showTimePicker = true
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
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(contentPadding = topBarPadding) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Lesson duration:",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        TextButton(onClick = { /*TODO show time picker*/ }) {
                            Text(
                                text = lessonDuration.parseNhNmin(),
                                style = MaterialTheme.typography.labelLarge,
                                color = Gray50
                            )
                        }
                    }
                }

                items(lessonTimes.size) {
                    BellScheduleListItem(
                        lessonTime = lessonTimes[it],
                        showDeleteAction = it == (lessonTimes.size - 1),
                        onItemClick = {
                            showTimePicker = true
                        },
                        onRemoveClick = {
                            //TODO item
                            scope.launch {
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "Lesson removed.",
                                        actionLabel = "Undo",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        //TODO show item
                                    }

                                    SnackbarResult.Dismissed -> {
                                        //TODO delete item
                                    }
                                }
                            }
                        }
                    )
                }
            }

            if (showTimePicker) {
                TimePickerDialog(
                    onCancel = {
                        showTimePicker = false
                    },
                    onConfirm = {
                        showTimePicker = false
                    },
                ) {
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            clockDialSelectedContentColor = Green,
                            clockDialColor = Gray90,
                            selectorColor = Gray50,
                            clockDialUnselectedContentColor = Gray60,
                            periodSelectorBorderColor = Gray70,
                            periodSelectorSelectedContainerColor = White,
                            periodSelectorUnselectedContainerColor = White,
                            periodSelectorSelectedContentColor = Green,
                            periodSelectorUnselectedContentColor = Gray80,
                            containerColor = White,
                            timeSelectorSelectedContainerColor = Gray90,
                            timeSelectorUnselectedContainerColor = Gray90,
                            timeSelectorSelectedContentColor = Green,
                            timeSelectorUnselectedContentColor = Gray60
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun BellScheduleListItem(
    lessonTime: LessonTime,
    showDeleteAction: Boolean = false,
    onRemoveClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .clickable {
                    onItemClick()
                }
                .fillMaxWidth()
                .padding(16.dp)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = lessonTime.number.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = Gray50
                )
                Text(
                    text = lessonTime.getTimeRange(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (showDeleteAction) {
                IconButton(modifier = Modifier.size(20.dp), onClick = {
                    onRemoveClick()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Delete item",
                        tint = Gray50
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp), thickness = 1.dp, color = Gray90
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TimePickerPreview() {
    var showTimePicker by remember { mutableStateOf(false) }
    val state = rememberTimePickerState()

    TimePickerDialog(
        onCancel = {
            showTimePicker = false
        },
        onConfirm = {
            showTimePicker = false
        },
    ) {
        TimePicker(
            state = state,
            colors = TimePickerDefaults.colors(
                clockDialSelectedContentColor = Green,
                clockDialColor = Gray90,
                selectorColor = Gray50,
                clockDialUnselectedContentColor = Gray60,
                periodSelectorBorderColor = Gray70,
                periodSelectorSelectedContainerColor = White,
                periodSelectorUnselectedContainerColor = White,
                periodSelectorSelectedContentColor = Green,
                periodSelectorUnselectedContentColor = Gray80,
                containerColor = White,
                timeSelectorSelectedContainerColor = Gray90,
                timeSelectorUnselectedContainerColor = Gray90,
                timeSelectorSelectedContentColor = Green,
                timeSelectorUnselectedContentColor = Gray60
            )
        )
    }
}
