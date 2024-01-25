package com.yetk.yetkschedule.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yetk.yetkschedule.data.remote.model.LessonTime
import com.yetk.yetkschedule.data.remote.viewmodel.BellScheduleViewModel
import com.yetk.yetkschedule.ui.ProgressBar
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray90

private const val TAG = "BellScheduleScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BellScheduleScreen(
    viewModel: BellScheduleViewModel = hiltViewModel(),
    bottomBarPadding: PaddingValues
) {
    LaunchedEffect(key1 = null) {
        viewModel.fetchBellScheduleData()
    }
    ProgressBar()
//    when (val bellSchedule = viewModel.bellScheduleResponse.value) {
//        is Loading -> ProgressBar()
//        is Success -> {
//            val lessonDurationMin = bellSchedule.data.lesson_duration_min.parseNhNmin()
//            val lessonsTime = bellSchedule.data.lessons_time
//            Scaffold(
//                modifier = Modifier.padding(bottomBarPadding),
//                topBar = {
//                    Column() {
//                        TopAppBar(
//                            modifier = Modifier.padding(end = 16.dp),
//                            title = {
//                                Text(
//                                    text = "Расписание звонков",
//                                    textAlign = TextAlign.Center,
//                                    style = MaterialTheme.typography.headlineLarge
//                                )
//                            },
//                        )
//                        Divider(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 16.dp), thickness = 1.dp, color = Gray90
//                        )
//                    }
//                },
//            ) { topBarPadding ->
//                Box(modifier = Modifier.fillMaxSize()) {
//                    LazyColumn(contentPadding = topBarPadding) {
//                        item {
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(vertical = 8.dp, horizontal = 16.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Text(
//                                    text = "Длительность пар: ",
//                                    style = MaterialTheme.typography.bodyMedium
//                                )
//                                Text(
//                                    text = lessonDurationMin,
//                                    style = MaterialTheme.typography.labelLarge,
//                                    color = Gray50
//                                )
//                            }
//                        }
//
//                        items(lessonsTime.size) {
//                            BellScheduleListItem(
//                                lessonsTime = LessonTime(
//                                    number = it + 1,
//                                    time = lessonsTime[it]
//                                ),
//                            )
//                        }
//                    }
//                }
//            }
//        }
//        is Failure -> print(TAG, bellSchedule.e)
//    }
}

@Composable
fun BellScheduleListItem(
    lessonsTime: LessonTime,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = lessonsTime.number.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = Gray50
                )
                Text(
                    text = lessonsTime.time,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp), thickness = 1.dp, color = Gray90
        )
    }
}
