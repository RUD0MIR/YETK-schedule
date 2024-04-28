package com.yetk.for_student.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yetk.designsystem.component.YetkTopBar
import com.yetk.designsystem.theme.Gray50
import com.yetk.designsystem.theme.Gray90
import com.yetk.for_student.parseNhNmin
import com.yetk.model.Response
import com.yetk.ui.ErrorScreen
import com.yetk.ui.LoadingScreen

private const val TAG = "BellScheduleScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BellScheduleScreen(
    viewModel: com.yetk.for_student.data.remote.viewmodel.StudentViewModel = hiltViewModel(),
    bottomBarPadding: PaddingValues
) {
    when (val bellSchedule = viewModel.bellSchedule.value) {
        is Response.Loading -> LoadingScreen(topBarTitle = "Расписание звонков")
        is Response.Failure -> {
            ErrorScreen(topBarTitle = "Расписание звонков")
        }
        is Response.Success -> {
            val lessonDurationMin = bellSchedule.data.lesson_duration_min.parseNhNmin()
            val lessonsTime = bellSchedule.data.lessons_time
            Scaffold(
                modifier = Modifier.padding(bottomBarPadding),
                topBar = {
                    YetkTopBar(text = "Расписание звонков") {}
                },
            ) { topBarPadding ->
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(contentPadding = topBarPadding) {
                        item {
                            BellScheduleHeader("Длительность пар: ", lessonDurationMin)
                        }

                        items(lessonsTime.size) {
                            BellScheduleListItem(
                                number = it + 1,
                                time = lessonsTime[it]
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BellScheduleHeader(text: String, lessonDurationMin: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = lessonDurationMin,
            style = MaterialTheme.typography.labelLarge,
            color = Gray50
        )
    }
}

@Composable
fun BellScheduleListItem(
    number: Int,
    time: String
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
                    text = number.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = Gray50
                )
                Text(
                    text = time,
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
