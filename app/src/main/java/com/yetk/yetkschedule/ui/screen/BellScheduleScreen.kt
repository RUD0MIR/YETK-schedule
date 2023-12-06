package com.yetk.yetkschedule.ui.screen

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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yetk.yetkschedule.data.local.model.LessonTime
import com.yetk.yetkschedule.other.parseNhNmin
import com.yetk.yetkschedule.ui.theme.Gray50
import com.yetk.yetkschedule.ui.theme.Gray90
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
    val lessonDuration by remember {
        mutableStateOf(Duration.ZERO.plus(90L.minutes))
    }

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
                            text = "Lesson duration: ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = lessonDuration.parseNhNmin(),
                            style = MaterialTheme.typography.labelLarge,
                            color = Gray50
                        )
                    }
                }

                items(lessonTimes.size) {
                    BellScheduleListItem(
                        lessonTime = lessonTimes[it],
                    )
                }
            }
        }
    }
}

@Composable
fun BellScheduleListItem(
    lessonTime: LessonTime,
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
                    text = lessonTime.number.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = Gray50
                )
                Text(
                    text = lessonTime.getTimeRange(),
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
