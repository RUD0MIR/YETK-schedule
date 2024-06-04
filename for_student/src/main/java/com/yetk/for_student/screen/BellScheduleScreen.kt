package com.yetk.for_student.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yetk.designsystem.theme.YetkScheduleTheme
import com.yetk.for_student.data.remote.viewmodel.StudentViewModel
import com.yetk.for_student.parseNhNmin
import com.yetk.model.BellSchedule
import com.yetk.model.Response
import com.yetk.ui.ErrorScreen
import com.yetk.ui.LoadingScreen

private const val TAG = "BellScheduleScreen"

@Composable
internal fun BellScheduleRoute(
    viewModel: StudentViewModel = hiltViewModel(),
) {
    BellScheduleScreen(
        viewModel.bellSchedule.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BellScheduleScreen(
    bellSchedule: Response<BellSchedule>
) {
    when (bellSchedule) {
        is Response.Loading -> LoadingScreen()
        is Response.Failure -> ErrorScreen()
        is Response.Success -> {
            val lessonDurationMin = bellSchedule.data.lesson_duration_min.parseNhNmin()
            val lessonsTime = bellSchedule.data.lessons_time

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
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
        )
    }
}

@Composable
fun BellScheduleListItem(
    number: Int,
    time: String
) {
    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
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
                .padding(start = 16.dp), thickness = 1.dp
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BellScheduleScreenPreview() {
    YetkScheduleTheme {
        Surface(tonalElevation = 5.dp) {
            BellScheduleScreen(
                bellSchedule =  Response.Success<BellSchedule>(
                    BellSchedule(
                        null,
                        90,
                        lessons_time = listOf(
                            "08:00 - 09:30",
                            "09:40 - 11:10",
                            "11:20 - 12:50",
                            "13:20 - 14:50",
                            "15:00 - 16:30",
                            "16:40 - 18:10"
                        )
                    )


                )
            )
        }
    }
}
